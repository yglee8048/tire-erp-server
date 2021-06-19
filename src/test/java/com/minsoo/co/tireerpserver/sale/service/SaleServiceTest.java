package com.minsoo.co.tireerpserver.sale.service;

import com.minsoo.co.tireerpserver.ServiceTest;
import com.minsoo.co.tireerpserver.management.entity.Brand;
import com.minsoo.co.tireerpserver.management.entity.Pattern;
import com.minsoo.co.tireerpserver.management.entity.Vendor;
import com.minsoo.co.tireerpserver.management.entity.Warehouse;
import com.minsoo.co.tireerpserver.purchase.entity.Purchase;
import com.minsoo.co.tireerpserver.purchase.entity.PurchaseContent;
import com.minsoo.co.tireerpserver.sale.code.SaleSource;
import com.minsoo.co.tireerpserver.sale.code.SaleStatus;
import com.minsoo.co.tireerpserver.sale.entity.Sale;
import com.minsoo.co.tireerpserver.sale.entity.SaleContent;
import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.stock.entity.Stock;
import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.user.entity.ClientCompany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.*;
import static org.assertj.core.api.Assertions.*;

class SaleServiceTest extends ServiceTest {

    @BeforeEach
    void setUp() {
        clientCompanyService.create(CLIENT_COMPANY("고객사"));
        Brand brand = brandService.create(BRAND("테스트 브랜드"));
        Pattern pattern = patternService.create(brand.getId(), PATTERN("테스트 패턴"));
        Tire tire = tireService.create(TIRE("PRODUCT_ID_01", pattern.getId(), 11));
        TireDot tireDot1 = tireDotService.create(tire.getId(), TIRE_DOT("1111", 2000));
        TireDot tireDot2 = tireDotService.create(tire.getId(), TIRE_DOT("2222", 4000));
        TireDot tireDot3 = tireDotService.create(tire.getId(), TIRE_DOT("3333", 6000));
        Vendor vendor = vendorService.create(VENDOR("매입처"));
        Warehouse warehouse = warehouseService.create(WAREHOUSE("창고"));
        Purchase purchase = purchaseService.create(PURCHASE(vendor.getId(), LocalDate.now(),
                PURCHASE_CONTENT(tireDot1.getId(), 2L),
                PURCHASE_CONTENT(tireDot2.getId(), 2L),
                PURCHASE_CONTENT(tireDot3.getId(), 2L)));
        List<PurchaseContent> purchaseContents = purchaseContentService.findAllByPurchaseId(purchase.getId());
        purchaseService.confirm(purchase.getId(),
                Arrays.asList(
                        PURCHASE_CONTENT_CONFIRM(purchaseContents.get(0).getId(), STOCK_MODIFY("default", warehouse.getId(), 2L, true)),
                        PURCHASE_CONTENT_CONFIRM(purchaseContents.get(1).getId(), STOCK_MODIFY("default", warehouse.getId(), 2L, true)),
                        PURCHASE_CONTENT_CONFIRM(purchaseContents.get(2).getId(), STOCK_MODIFY("default", warehouse.getId(), 2L, true))));
    }

    @Test
    @DisplayName("매출 생성")
    void create() {
        // GIVEN
        List<ClientCompany> clientCompanies = clientCompanyService.findAll();
        ClientCompany clientCompany = clientCompanies.get(0);
        List<Tire> tires = tireService.findAll();
        Tire tire = tires.get(0);
        List<TireDot> tireDots = tireDotService.findAllByTireId(tire.getId());

        // WHEN
        Sale sale = saleService.create(SALE(clientCompany.getId(), LocalDate.now(), LocalDate.now(),
                SALE_CONTENT(tireDots.get(0).getId(), 1L),
                SALE_CONTENT(tireDots.get(1).getId(), 2L),
                SALE_CONTENT(tireDots.get(0).getId(), 3L)));

        // THEN
        assertThat(sale.getStatus()).isEqualTo(SaleStatus.REQUESTED);
        assertThat(sale.getClientCompany().getId()).isEqualTo(clientCompany.getId());
        assertThat(sale.getDelivery()).isNull();
        assertThat(sale.getTransactionDate()).isEqualTo(LocalDate.now());
        assertThat(sale.getSource()).isEqualTo(SaleSource.MANUAL);
        assertThat(sale.getContents().size()).isEqualTo(2);
        assertThat(sale.getContents()
                .stream()
                .filter(saleContent -> saleContent.getTireDot().getId().equals(tireDots.get(0).getId()))
                .findAny()
                .orElseThrow().getQuantity()).isEqualTo(4L);
    }

    @Test
    @DisplayName("매출 수정")
    void update() {
        // GIVEN
        List<ClientCompany> clientCompanies = clientCompanyService.findAll();
        ClientCompany clientCompany = clientCompanies.get(0);
        List<Tire> tires = tireService.findAll();
        Tire tire = tires.get(0);
        List<TireDot> tireDots = tireDotService.findAllByTireId(tire.getId());
        Sale sale = saleService.create(SALE(clientCompany.getId(), LocalDate.now(), LocalDate.now(),
                SALE_CONTENT(tireDots.get(0).getId(), 1L),
                SALE_CONTENT(tireDots.get(1).getId(), 2L)));
        SaleContent saleContent1 = sale.getContents().stream()
                .filter(saleContent -> saleContent.getTireDot().getId().equals(tireDots.get(0).getId()))
                .findAny()
                .orElseThrow();

        // WHEN
        Sale updated = saleService.update(sale.getId(), SALE(clientCompany.getId(), LocalDate.now().plusDays(1), LocalDate.now(),
                SALE_CONTENT(tireDots.get(2).getId(), 3L),
                SALE_CONTENT(tireDots.get(1).getId(), 2L),
                SALE_CONTENT(tireDots.get(1).getId(), 1L)));
        em.flush();

        // THEN
        assertThat(sale.getId()).isEqualTo(updated.getId());
        assertThat(updated.getTransactionDate()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(updated.getContents().size()).isEqualTo(2);
        assertThat(updated.getContents()
                .stream()
                .filter(saleContent -> saleContent.getTireDot().getId().equals(tireDots.get(1).getId()))
                .findAny()
                .orElseThrow().getQuantity()).isEqualTo(3L);
        assertThat(updated.getContents()
                .stream()
                .filter(saleContent -> saleContent.getTireDot().getId().equals(tireDots.get(2).getId()))
                .findAny()
                .orElseThrow().getQuantity()).isEqualTo(3L);
        assertThatThrownBy(() -> saleContentService.findById(saleContent1.getId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("매출 확정")
    void confirm() {
        // GIVEN
        List<ClientCompany> clientCompanies = clientCompanyService.findAll();
        ClientCompany clientCompany = clientCompanies.get(0);
        List<Tire> tires = tireService.findAll();
        Tire tire = tires.get(0);
        List<TireDot> tireDots = tireDotService.findAllByTireId(tire.getId());
        Sale sale = saleService.create(SALE(clientCompany.getId(), LocalDate.now(), LocalDate.now(),
                SALE_CONTENT(tireDots.get(0).getId(), 1L),
                SALE_CONTENT(tireDots.get(1).getId(), 2L)));
        SaleContent saleContent1 = sale.getContents().stream()
                .filter(saleContent -> saleContent.getTireDot().getId().equals(tireDots.get(0).getId()))
                .findAny().orElseThrow();
        SaleContent saleContent2 = sale.getContents().stream()
                .filter(saleContent -> saleContent.getTireDot().getId().equals(tireDots.get(1).getId()))
                .findAny().orElseThrow();
        List<Stock> stocks_1 = stockService.findByTireDotId(tireDots.get(0).getId());
        Stock stock1 = stocks_1.get(0);
        List<Stock> stocks_2 = stockService.findByTireDotId(tireDots.get(1).getId());
        Stock stock2 = stocks_2.get(0);

        // WHEN
        assertThatThrownBy(() -> saleService.confirm(sale.getId(),
                Arrays.asList(
                        SALE_CONTENT_CONFIRM(saleContent1.getId(), SALE_STOCK(stock1.getId(), 3L)),
                        SALE_CONTENT_CONFIRM(saleContent2.getId(), SALE_STOCK(stock2.getId(), 2L)))))
                .isInstanceOf(BadRequestException.class);

        Sale confirmed = saleService.confirm(sale.getId(),
                Arrays.asList(
                        SALE_CONTENT_CONFIRM(saleContent1.getId(), SALE_STOCK(stock1.getId(), 1L)),
                        SALE_CONTENT_CONFIRM(saleContent2.getId(), SALE_STOCK(stock2.getId(), 2L))));

        // THEN
        assertThat(confirmed.getStatus()).isEqualTo(SaleStatus.CONFIRMED);
        assertThat(stock1.getQuantity()).isEqualTo(1L);
        assertThat(stock2.getQuantity()).isEqualTo(0L);
        assertThatThrownBy(() -> saleService.update(confirmed.getId(),
                SALE(clientCompany.getId(), LocalDate.now(), LocalDate.now(),
                        SALE_CONTENT(tireDots.get(0).getId(), 2L),
                        SALE_CONTENT(tireDots.get(1).getId(), 2L))))
                .isInstanceOf(AlreadyConfirmedException.class);
        assertThatThrownBy(() -> saleService.confirm(confirmed.getId(),
                Arrays.asList(
                        SALE_CONTENT_CONFIRM(saleContent1.getId(), SALE_STOCK(stock1.getId(), 1L)),
                        SALE_CONTENT_CONFIRM(saleContent2.getId(), SALE_STOCK(stock2.getId(), 2L)))))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("매출 취소")
    void cancelById() {
        // GIVEN

        // WHEN

        // THEN

    }
}