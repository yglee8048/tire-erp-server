package com.minsoo.co.tireerpserver.sale.api;

import com.minsoo.co.tireerpserver.sale.model.content.SaleContentConfirmRequest;
import com.minsoo.co.tireerpserver.sale.model.content.SaleContentResponse;
import com.minsoo.co.tireerpserver.sale.model.delivery.DeliveryRequest;
import com.minsoo.co.tireerpserver.sale.model.delivery.DeliveryResponse;
import com.minsoo.co.tireerpserver.sale.model.memo.SaleMemoRequest;
import com.minsoo.co.tireerpserver.sale.model.memo.SaleMemoResponse;
import com.minsoo.co.tireerpserver.sale.service.DeliveryService;
import com.minsoo.co.tireerpserver.sale.service.SaleMemoService;
import com.minsoo.co.tireerpserver.shared.model.ApiResponse;
import com.minsoo.co.tireerpserver.sale.model.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.sale.model.SaleRequest;
import com.minsoo.co.tireerpserver.sale.model.SaleResponse;
import com.minsoo.co.tireerpserver.sale.service.SaleContentService;
import com.minsoo.co.tireerpserver.sale.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class SaleApi {

    private final SaleService saleService;
    private final SaleContentService saleContentService;
    private final SaleMemoService saleMemoService;
    private final DeliveryService deliveryService;

    // GRID
    @GetMapping(value = "/sale-content-grid")
    @Operation(summary = "GRID 매출 항목 전체 목록 조회", description = "매출 내역 목록을 보여줄 때 사용한다.(Aggregation)")
    public ApiResponse<List<SaleContentGridResponse>> findAllSaleContentGrid(@RequestParam(required = false) LocalDate from,
                                                                             @RequestParam(required = false) LocalDate to) {
        return ApiResponse.OK(saleContentService.findGridsByTransactionDate(from, to));
    }

    @GetMapping(value = "/sale-content-grid/{saleId}")
    @Operation(summary = "GRID 매출 항목 목록 조회", description = "매출 상세 내역에서 사용한다.")
    public ApiResponse<List<SaleContentGridResponse>> findSaleContentGridBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(saleContentService.findGridsBySaleId(saleId));
    }

    // SALE
    @GetMapping(value = "/sales")
    @Operation(summary = "매출 목록 조회")
    public ApiResponse<List<SaleResponse>> findSales() {
        return ApiResponse.OK(saleService.findAll()
                .stream()
                .map(SaleResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/sales/{saleId}")
    @Operation(summary = "매출 상세 조회")
    public ApiResponse<SaleResponse> findSaleById(@PathVariable Long saleId) {
        return ApiResponse.OK(SaleResponse.of(saleService.findById(saleId)));
    }

    @PostMapping(value = "/sales")
    @Operation(summary = "매출 생성")
    public ResponseEntity<ApiResponse<Void>> createSale(@RequestBody @Valid SaleRequest saleRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(SaleApi.class).findSaleById(saleService.create(saleRequest).getId())).toUri());
    }

    @PutMapping(value = "/sales/{saleId}")
    @Operation(summary = "매출 수정")
    public ApiResponse<Void> updateSale(@PathVariable Long saleId,
                                        @RequestBody @Valid SaleRequest saleRequest) {
        saleService.update(saleId, saleRequest);
        return ApiResponse.OK;
    }

    @PostMapping(value = "/sales/{saleId}/confirm")
    @Operation(summary = "매출 확정")
    public ApiResponse<Void> confirmSale(@PathVariable Long saleId,
                                         @RequestBody @Valid List<SaleContentConfirmRequest> saleContentConfirmRequests) {
        saleService.confirm(saleId, saleContentConfirmRequests);
        return ApiResponse.OK;
    }

    @PostMapping(value = "/sales/{saleId}/cancel")
    @Operation(summary = "매출 취소")
    public ApiResponse<Void> deleteSaleById(@PathVariable Long saleId) {
        saleService.cancelById(saleId);
        return ApiResponse.OK;
    }

    // SALE-CONTENT
    @GetMapping(value = "/sales/{saleId}/sale-contents")
    @Operation(summary = "매출 항목 목록 조회")
    public ApiResponse<List<SaleContentResponse>> findSaleContentsBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(saleContentService.findAllBySaleId(saleId)
                .stream()
                .map(SaleContentResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/sales/{saleId}/sale-contents/{saleContentId}")
    @Operation(summary = "매출 항목 상세 조회")
    public ApiResponse<SaleContentResponse> findSaleContentById(@PathVariable Long saleId,
                                                                @PathVariable Long saleContentId) {
        return ApiResponse.OK(SaleContentResponse.of(saleContentService.findById(saleContentId)));
    }

    // SALE-MEMO
    @GetMapping(value = "/sales/{saleId}/sale-memos")
    @Operation(summary = "매출 메모 목록 조회")
    public ApiResponse<List<SaleMemoResponse>> findSaleMemosBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(saleMemoService.findAllBySaleId(saleId)
                .stream()
                .map(SaleMemoResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/sales/{saleId}/sale-memos/{saleMemoId}")
    @Operation(summary = "매출 메모 상세 조회")
    public ApiResponse<SaleMemoResponse> findSaleMemoById(@PathVariable Long saleId,
                                                          @PathVariable Long saleMemoId) {
        return ApiResponse.OK(SaleMemoResponse.of(saleMemoService.findById(saleMemoId)));
    }

    @PostMapping(value = "/sales/{saleId}/sale-memos")
    @Operation(summary = "매출 메모 생성")
    public ResponseEntity<ApiResponse<Void>> createSaleMemo(@PathVariable Long saleId,
                                                            @RequestBody @Valid SaleMemoRequest saleMemoRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(SaleApi.class)
                        .findSaleMemoById(saleId, saleMemoService.create(saleId, saleMemoRequest).getId())).toUri());
    }

    @PutMapping(value = "/sales/{saleId}/sale-memos/{saleMemoId}")
    @Operation(summary = "매출 메모 수정")
    public ApiResponse<Void> updateSaleMemo(@PathVariable Long saleId,
                                            @PathVariable Long saleMemoId,
                                            @RequestBody @Valid SaleMemoRequest saleMemoRequest) {
        saleMemoService.update(saleMemoId, saleMemoRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/sales/{saleId}/sale-memos/{saleMemoId}")
    @Operation(summary = "매출 메모 삭제")
    public ApiResponse<Void> deleteSaleMemo(@PathVariable Long saleId,
                                            @PathVariable Long saleMemoId) {
        saleMemoService.removeById(saleMemoId);
        return ApiResponse.OK;
    }

    // DELIVERY
    @GetMapping(value = "/sales/{saleId}/deliveries")
    @Operation(summary = "배송 목록 조회")
    public ApiResponse<List<DeliveryResponse>> findDeliveriesBySaleId(@PathVariable Long saleId) {
        return ApiResponse.OK(deliveryService.findBySaleId(saleId)
                .stream()
                .map(DeliveryResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/sales/{saleId}/deliveries/{deliveryId}")
    @Operation(summary = "배송 조회")
    public ApiResponse<DeliveryResponse> findDeliveryById(@PathVariable Long saleId, @PathVariable Long deliveryId) {
        return ApiResponse.OK(DeliveryResponse.of(deliveryService.findById(deliveryId)));
    }

    @PostMapping(value = "/sales/{saleId}/deliveries")
    @Operation(summary = "배송 생성")
    public ResponseEntity<ApiResponse<Void>> createDelivery(@PathVariable Long saleId,
                                                            @RequestBody @Valid DeliveryRequest deliveryRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(SaleApi.class).findDeliveryById(saleId, deliveryService.create(saleId, deliveryRequest).getId())).toUri());
    }

    @PutMapping(value = "/sales/{saleId}/deliveries/{deliveryId}")
    @Operation(summary = "배송 수정")
    public ApiResponse<Void> updateDelivery(@PathVariable Long saleId,
                                            @PathVariable Long deliveryId,
                                            @RequestBody @Valid DeliveryRequest deliveryRequest) {
        deliveryService.update(saleId, deliveryId, deliveryRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/sales/{saleId}/deliveries/{deliveryId}")
    @Operation(summary = "배송 삭제")
    public ApiResponse<Void> deleteDelivery(@PathVariable Long saleId, @PathVariable Long deliveryId) {
        deliveryService.removeById(deliveryId);
        return ApiResponse.OK;
    }
}
