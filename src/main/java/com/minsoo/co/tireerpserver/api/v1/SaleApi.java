package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.sale.SaleResponse;
import com.minsoo.co.tireerpserver.model.dto.sale.content.SaleContentResponse;
import com.minsoo.co.tireerpserver.model.dto.sale.memo.SaleMemoResponse;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/sales")
@RequiredArgsConstructor
public class SaleApi {

    @GetMapping
    @ApiOperation(value = "매출 내역 목록 조회", notes = "매출 내역의 목록을 조회한다.")
    public ApiResponse<List<SaleResponse>> findAllSales() {
        return null;
    }

    @GetMapping("/{saleId}")
    @ApiOperation(value = "매출 내역 상세 조회", notes = "매출 내역의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saleId", value = "매출 ID", example = "201324", required = true)})
    public ApiResponse<SaleResponse> findSaleById(@PathVariable(value = "saleId") Long saleId) {
        return null;
    }

    // sale contents
    @GetMapping("/{saleId}/sale-contents")
    @ApiOperation(value = "매출 항목 목록 조회", notes = "매출 항목의 목록을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saleId", value = "매출 ID", example = "201324", required = true)})
    public ApiResponse<List<SaleContentResponse>> findSaleContentsBySaleId(@PathVariable(value = "saleId") Long saleId) {
        return null;
    }

    // sale-memos
    @GetMapping("/{saleId}/sale-memos")
    @ApiOperation(value = "매출 메모 목록 조회", notes = "매출 메모의 목록을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saleId", value = "매출 ID", example = "201324", required = true)})
    public ApiResponse<List<SaleMemoResponse>> findSaleMemosBySaleId(@PathVariable(value = "saleId") Long saleId) {
        return null;
    }
}
