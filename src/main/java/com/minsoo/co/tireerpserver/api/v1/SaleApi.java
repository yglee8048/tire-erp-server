package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.sale.SaleFlatResponse;
import com.minsoo.co.tireerpserver.model.dto.sale.SaleSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.sale.content.SaleContentResponse;
import com.minsoo.co.tireerpserver.model.dto.sale.content.SaleContentUpdateRequest;
import com.minsoo.co.tireerpserver.model.dto.sale.memo.SaleMemoResponse;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.service.SaleService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/sales")
@RequiredArgsConstructor
public class SaleApi {

    private final SaleService saleService;

    @GetMapping
    @Tag(name = "매출 내역 목록 조회", description = "매출 내역의 목록을 조회한다.")
    public ApiResponse<List<SaleFlatResponse>> findAllSales() {
        return ApiResponse.createOK(saleService.findAll());
    }

    @GetMapping("/{saleId}")
    @Tag(name = "매출 내역 상세 조회", description = "매출 내역의 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "saleId", description = "매출 ID", example = "201324", required = true)})
    public ApiResponse<List<SaleFlatResponse>> findSaleById(@PathVariable(value = "saleId") Long saleId) {
        return ApiResponse.createOK(saleService.findById(saleId));
    }

    @PostMapping
    @Tag(name = "매출 생성", description = "매출을 생성한다.")
    public ApiResponse<SaleSimpleResponse> createSale(@RequestBody @Valid SaleCreateRequest createRequest) {
        return ApiResponse.createOK(saleService.create(createRequest));
    }

    // sale contents
    @GetMapping("/{saleId}/sale-contents")
    @Tag(name = "매출 항목 목록 조회", description = "매출 항목의 목록을 조회한다.")
    @Parameters({@Parameter(name = "saleId", description = "매출 ID", example = "201324", required = true)})
    public ApiResponse<List<SaleContentResponse>> findSaleContentsBySaleId(@PathVariable(value = "saleId") Long saleId) {
        return null;
    }

    @PutMapping("/{saleId}/sale-contents/{saleContentId}")
    @Tag(name = "매출 항목 수정", description = "매출 항목을 수정한다.")
    @Parameters({
            @Parameter(name = "saleId", description = "매출 ID", example = "201324", required = true),
            @Parameter(name = "saleContentId", description = "매출 항목 ID", example = "201324", required = true)})
    public ApiResponse<List<SaleContentResponse>> updateSaleContent(@PathVariable(value = "saleId") Long saleId,
                                                                    @PathVariable(value = "saleContentId") Long saleContentId,
                                                                    @RequestBody @Valid SaleContentUpdateRequest updateRequest) {
        return null;
    }

    // sale-memos
    @GetMapping("/{saleId}/sale-memos")
    @Tag(name = "매출 메모 목록 조회", description = "매출 메모의 목록을 조회한다.")
    @Parameters({@Parameter(name = "saleId", description = "매출 ID", example = "201324", required = true)})
    public ApiResponse<List<SaleMemoResponse>> findSaleMemosBySaleId(@PathVariable(value = "saleId") Long saleId) {
        return null;
    }
}
