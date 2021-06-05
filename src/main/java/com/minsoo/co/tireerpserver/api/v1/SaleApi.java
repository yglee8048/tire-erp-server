//package com.minsoo.co.tireerpserver.api.v1;
//
//import com.minsoo.co.tireerpserver.model.dto.sale.SaleCreateRequest;
//import com.minsoo.co.tireerpserver.model.dto.sale.SaleFlatResponse;
//import com.minsoo.co.tireerpserver.model.dto.sale.memo.SaleMemoResponse;
//import com.minsoo.co.tireerpserver.sale.entity.Sale;
//import com.minsoo.co.tireerpserver.api.v1.model.ApiResponse;
//import com.minsoo.co.tireerpserver.service.sale.SaleService;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/api/v1/sales")
//@RequiredArgsConstructor
//public class SaleApi {
//
//    private final SaleService saleService;
//
//    @GetMapping
//    @Operation(summary = "매출 내역 목록 조회", description = "매출 내역의 목록을 조회한다.")
//    public ApiResponse<List<SaleFlatResponse>> findAllSales() {
//        return ApiResponse.OK(saleService.findAll()
//                .stream()
//                .map(SaleFlatResponse::of)
//                .collect(Collectors.toList()));
//    }
//
//    @GetMapping("/{saleId}")
//    @Operation(summary = "매출 내역 상세 조회", description = "매출 내역의 상세 정보를 조회한다.")
//    public ApiResponse<List<SaleFlatResponse>> findSaleById(@PathVariable(value = "saleId") Long saleId) {
//        return ApiResponse.OK(saleService.findById(saleId)
//                .stream()
//                .map(SaleFlatResponse::of)
//                .collect(Collectors.toList()));
//    }
//
//    @PostMapping
//    @Operation(summary = "매출 생성", description = "매출을 생성한다.")
//    public ResponseEntity<ApiResponse<Object>> createSale(@RequestBody @Valid SaleCreateRequest createRequest) {
//        Sale sale = saleService.create(createRequest);
//        return ApiResponse.CREATED(
//                linkTo(methodOn(SaleApi.class).findSaleById(sale.getId())).toUri());
//    }
//
//    // sale-memos
//    @GetMapping("/{saleId}/sale-memos")
//    @Operation(summary = "매출 메모 목록 조회", description = "매출 메모의 목록을 조회한다.")
//    public ApiResponse<List<SaleMemoResponse>> findSaleMemosBySaleId(@PathVariable(value = "saleId") Long saleId) {
//        return null;
//    }
//}
