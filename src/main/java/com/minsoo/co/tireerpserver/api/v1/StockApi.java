package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.stock.*;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.service.StockService;
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
@RequestMapping(value = "/api/v1/stocks")
@RequiredArgsConstructor
public class StockApi {

    private final StockService stockService;

    @GetMapping
    @Tag(name = "재고 전체 목록 조회", description = "재고 전체 목록을 조회한다.")
    public ApiResponse<List<StockResponse>> findAllStocks() {
        return ApiResponse.createOK(stockService.findAll());
    }

    @GetMapping(value = "/{stockId}")
    @Tag(name = "재고 상세 조회", description = "재고의 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "stockId", description = "재고 ID", example = "201324", required = true)})
    public ApiResponse<StockResponse> findStockById(@PathVariable(value = "stockId") Long stockId) {
        return ApiResponse.createOK(stockService.findById(stockId));
    }

    @PostMapping(value = "/{stockId}/update-lock")
    @Tag(name = "재고 Lock 수정", description = "재고의 공개 여부를 수정한다.")
    @Parameters({@Parameter(name = "stockId", description = "재고 ID", example = "201324", required = true)})
    public ApiResponse<StockSimpleResponse> updateStockLock(@PathVariable(value = "stockId") Long stockId,
                                                            @RequestBody @Valid StockUpdateLockRequest updateLockRequest) {
        return ApiResponse.createOK(stockService.updateStockLock(stockId, updateLockRequest));
    }

    @PostMapping(value = "/{stockId}/move-stock")
    @Tag(name = "재고 이동", description = "타이어 DOT 재고를 이동한다.")
    @Parameters({@Parameter(name = "stockId", description = "재고 ID", example = "201324", required = true)})
    public ApiResponse<List<StockSimpleResponse>> moveStock(@PathVariable(value = "stockId") Long stockId,
                                                            @RequestBody @Valid MoveStockRequest moveStockRequest) {
        return ApiResponse.createOK(stockService.moveStock(stockId, moveStockRequest));
    }

    // TIRE-STOCKS
    @GetMapping(value = "/tires")
    @Tag(name = "타이어 기준 재고 목록 조회", description = "재고를 타이어 기준으로 group by 한 목록을 조회한다.")
    @Parameters({
            @Parameter(name = "size", description = "사이즈 (단면폭/편평비/인치가 연결된 7자리 문자열)", example = "2454518"),
            @Parameter(name = "brand_name", description = "브랜드 이름", example = "피렐리"),
            @Parameter(name = "pattern", description = "패턴", example = "KL33"),
            @Parameter(name = "product_id", description = "상품 아이디", example = "P2454518Z")})
    public ApiResponse<List<TireStockResponse>> findTireStocks(@RequestParam(value = "size", required = false) String size,
                                                               @RequestParam(value = "brand_name", required = false) String brandName,
                                                               @RequestParam(value = "pattern", required = false) String pattern,
                                                               @RequestParam(value = "product_id", required = false) String productId) {
        return ApiResponse.createOK(stockService.findTireStocks(size, brandName, pattern, productId));
    }

    @GetMapping(value = "/tires/params")
    @Tag(name = "타이어 기준 재고 필터 조건 조회", description = "타이어 기준 재고를 조회할 때 필터할 수 있는 조건 목록을 조회한다.")
    public ApiResponse<TireStockParams> findTireStockParams() {
        return ApiResponse.createOK(stockService.findTireStockParams());
    }

    @GetMapping(value = "/tires/{tireId}")
    @Tag(name = "타이어 기준 재고 상세 조회", description = "재고를 타이어 기준으로 group by 한 상세 정보를 조회한다.")
    @Parameters({@Parameter(name = "tireId", description = "타이어 ID", example = "201324", required = true)})
    public ApiResponse<TireStockResponse> findTireStockByTireId(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponse.createOK(stockService.findTireStockByTireId(tireId));
    }

    @GetMapping(value = "/tires/{tireId}/dots")
    @Tag(name = "타이어 하위 재고 목록 조회", description = "입력한 타이어에 해당하는 재고 목록을 조회한다.")
    @Parameters({@Parameter(name = "tireId", description = "타이어 ID", example = "201324", required = true)})
    public ApiResponse<List<StockSimpleResponse>> findTireDotStocks(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponse.createOK(stockService.findAllByTireId(tireId));
    }
}
