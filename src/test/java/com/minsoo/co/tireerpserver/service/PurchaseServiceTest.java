package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.assertj.core.api.Assertions.*;


@Transactional
@SpringBootTest
class PurchaseServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PurchaseServiceTest.class);

    @Autowired
    EntityManager em;

    @Autowired
    BrandService brandService;

    @Autowired
    VendorService vendorService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    TireService tireService;

    @Autowired
    TireDotService tireDotService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    StockService stockService;

    /**
     * 1. 매입 생성 테스트
     * 1-1) 매입 생성 요청 중 이미 존재하는 dot 에 대한 요청인 경우, dot 를 새로 생성하지 않는다.
     * 1-2) 존재하지 않는 dot 에 대한 요청인 경우, dot 를 새로 생성한다.
     * 2. 매입 수정/삭제 테스트
     * 2-1) 매입이 확정되기 전에는 매입 내용을 수정할 수 있다.
     * 2-2) 매입이 확정되기 전에는 매입 내용을 삭제할 수 있다.
     * 3. 타이어 삭제 테스트
     * 3-1) 매입이 확정되기 전에는 재고에 반영되지 않는다.
     * 3-2) 재고가 없으면 타이어를 삭제할 수 있다.
     * 3-3) 타이어가 삭제되면 하위의 dot 도 함께 모두 삭제된다.
     * 3-4) 타이어가 삭제되면 관련된 매입 내역도 모두 삭제된다.
     */
    @Test
    @DisplayName("매입 생성 & 수정 & 삭제 테스트")
    void purchaseTest() {
        log.info("초기 데이터 생성");
        BrandResponse brand = brandService.create(BRAND("테스트 브랜드"));
        VendorResponse vendor = vendorService.create(VENDOR("테스트 매입처"));
        WarehouseResponse warehouse = warehouseService.create(WAREHOUSE("테스트 창고"));
        TireResponse tire = tireService.create(TIRE(brand.getBrandId(), "PRODUCT_ID_01", "테스트 타이어"));

        clear();

        log.info("1. 매입 생성 테스트");
        log.debug("매입이 이루어지기 전에는 dot 가 없다.");
        assertThat(tireDotService.findAllByTireId(tire.getTireId()).size()).isEqualTo(0);

        clear();

        log.debug("매입 생성 1");
        List<PurchaseSimpleResponse> purchaseList01 = purchaseService.create(CREATE_PURCHASE(vendor.getVendorId(),
                CREATE_PURCHASE_CONTENT(tire.getTireId(), "dot01", warehouse.getWarehouseId(), 1L),
                CREATE_PURCHASE_CONTENT(tire.getTireId(), "dot02", warehouse.getWarehouseId(), 2L)));

        log.debug("매입 생성 검증");
        PurchaseSimpleResponse purchase01 = purchaseList01.get(0);
        PurchaseSimpleResponse purchase02 = purchaseList01.get(1);

        assertThat(purchaseList01.size()).isEqualTo(2);
        assertThat(purchase01.getVendorId()).isEqualTo(vendor.getVendorId());
        assertThat(purchase01.getWarehouseId()).isEqualTo(warehouse.getWarehouseId());
        assertThat(purchase01.getPurchasedDate()).isEqualTo(LocalDate.now());

        log.debug("매입이 일어난 후 dot 가 생성되어야 한다.");
        assertThat(tireDotService.findAllByTireId(tire.getTireId()).size()).isEqualTo(2);

        clear();

        log.debug("dot 생성 검증");
        TireDotResponse dot01 = tireDotService.findById(purchase01.getTireDot().getTireDotId());
        TireDotResponse dot02 = tireDotService.findById(purchase02.getTireDot().getTireDotId());
        assertThat(dot01.getDot()).isEqualTo("dot01");
        assertThat(dot02.getDot()).isEqualTo("dot02");
        assertThat(dot01.getTireDotId()).isNotEqualTo(dot02.getTireDotId());

        clear();

        log.debug("매입 생성 2");
        List<PurchaseSimpleResponse> purchaseList02 = purchaseService.create(CREATE_PURCHASE(vendor.getVendorId(),
                CREATE_PURCHASE_CONTENT(tire.getTireId(), "dot01", warehouse.getWarehouseId(), 1L)));

        log.debug("dot 는 추가로 생성되지 않아야 한다.");
        assertThat(tireDotService.findAllByTireId(tire.getTireId()).size()).isEqualTo(2);
        assertThat(purchaseList02.get(0).getTireDot().getTireDotId()).isEqualTo(dot01.getTireDotId());

        clear();

        log.info("2. 매입 수정/삭제 테스트");
        log.debug("수정 테스트");
        PurchaseSimpleResponse updatedPurchase01 = purchaseService.update(purchase01.getPurchaseId(), UPDATE_PURCHASE(vendor.getVendorId(), tire.getTireId(), "dot01", warehouse.getWarehouseId(), 50L));
        assertThat(updatedPurchase01.getPurchaseId()).isEqualTo(purchase01.getPurchaseId());
        assertThat(updatedPurchase01.getQuantity()).isEqualTo(50L);

        clear();

        log.debug("삭제 테스트");
        purchaseService.removeById(purchase01.getPurchaseId());
        assertThatThrownBy(() -> purchaseService.findById(purchase01.getPurchaseId()))
                .isInstanceOf(NotFoundException.class);

        clear();

        log.info("3. 타이어 삭제 테스트");
        log.debug("매입이 확정되기 전에는 재고에 반영되지 않는다.");
        assertThat(stockService.findAll().size()).isEqualTo(0);

        log.debug("타이어 삭제");
        tireService.removeById(tire.getTireId());
        assertThatThrownBy(() -> tireService.findById(tire.getTireId()))
                .isInstanceOf(NotFoundException.class);

        clear();

        log.debug("하위 dot 도 모두 삭제되어야 한다.");
        assertThatThrownBy(() -> tireDotService.findById(dot01.getTireDotId()))
                .isInstanceOf(NotFoundException.class);
        assertThatThrownBy(() -> tireDotService.findById(dot02.getTireDotId()))
                .isInstanceOf(NotFoundException.class);

        log.debug("관련된 매입 내역도 모두 삭제되어야 한다.");
        assertThatThrownBy(() -> purchaseService.findById(purchase02.getPurchaseId()))
                .isInstanceOf(NotFoundException.class);

        clear();
    }

    /**
     * 1. 매입 확정 테스트
     * 1-1) 매입의 상태가 확정으로 변경되어야 한다.
     * 1-2) 매입이 확정되면 수정/삭제할 수 없다. -> 예외가 발생해야 한다.
     * 1-3) 매입이 확정되면 해당 내용이 재고에 반영되어야 한다.
     * 2. 타이어 삭제 테스트
     * 2-1) 재고가 존재하는 경우 타이어를 삭제할 수 없다. -> 예외가 발생해야 한다.
     */
    public void purchaseConfirmTest() {

    }

    private void clear() {
        em.flush();
        em.clear();
    }
}