package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.api.error.errors.BadRequestException;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.MoveStockRequest;
import com.minsoo.co.tireerpserver.model.dto.stock.StockResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockParams;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.service.StockService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/stocks")
@RequiredArgsConstructor
public class StockApi {

    private final StockService stockService;

    @GetMapping
    @ApiOperation(value = "재고 전체 목록 조회", notes = "재고 전체 목록을 조회한다.")
    public ApiResponse<List<StockResponse>> findAllStocks() {
        return ApiResponse.createOK(null);
    }

    @GetMapping(value = "/{stockId}")
    @ApiOperation(value = "재고 상세 조회", notes = "재고의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stockId", value = "재고 ID", example = "201324", required = true)})
    public ApiResponse<StockResponse> findStockById(@PathVariable(value = "stockId") Long stockId) {
        return ApiResponse.createOK(null);
    }

    @PostMapping(value = "/{stockId}/update-lock")
    @ApiOperation(value = "재고 Lock 수정", notes = "재고의 공개 여부를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stockId", value = "재고 ID", example = "201324", required = true),
            @ApiImplicitParam(name = "lock", value = "잠금 여부(true = 잠금 / false = 공개)", required = true)})
    private ApiResponse<StockResponse> updateStockLock(@PathVariable(value = "stockId") Long stockId,
                                                       @RequestParam(value = "lock") boolean lock) {
        return ApiResponse.createOK(StockResponse.of(stockService.updateStockLock(stockId, lock)));
    }

    @PostMapping(value = "/{stockId}/move-stock")
    @ApiOperation(value = "재고 이동", notes = "타이어 DOT 재고를 이동한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stockId", value = "재고 ID", example = "201324", required = true)})
    private ApiResponse<List<StockResponse>> moveStock(@PathVariable(value = "stockId") Long stockId,
                                                       @RequestBody @Valid MoveStockRequest moveStockRequest) {
        return ApiResponse.createOK(stockService.moveStock(stockId, moveStockRequest)
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }

    // TIRE-STOCKS
    @GetMapping(value = "/tires")
    @ApiOperation(value = "타이어 기준 재고 목록 조회", notes = "재고를 타이어 기준으로 group by 한 목록을 조회한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "size", value = "사이즈 (단면폭/편평비/인치가 연결된 7자리 문자열)", example = "2454518"),
            @ApiImplicitParam(name = "brand_name", value = "브랜드 이름", example = "피렐리"),
            @ApiImplicitParam(name = "pattern", value = "패턴", example = "KL33"),
            @ApiImplicitParam(name = "product_id", value = "상품 아이디", example = "P2454518Z")})
    private ApiResponse<List<TireStockResponse>> findTireStocks(@RequestParam(value = "size", required = false) String size,
                                                                @RequestParam(value = "brand_name", required = false) String brandName,
                                                                @RequestParam(value = "pattern", required = false) String pattern,
                                                                @RequestParam(value = "product_id", required = false) String productId) {
        // validation
        if (size != null && size.length() != 7) {
            throw new BadRequestException("size 의 입력 형식이 잘못되었습니다.");
        }
        return ApiResponse.createOK(stockService.findTireStocks(size, brandName, pattern, productId));
    }

    @GetMapping(value = "/tires/params")
    @ApiOperation(value = "타이어 기준 재고 필터 조건 조회", notes = "타이어 기준 재고를 조회할 때 필터할 수 있는 조건 목록을 조회한다.")
    private ApiResponse<TireStockParams> findTireStockParams() {
        return ApiResponse.createOK(stockService.findTireStockParams());
    }

    @GetMapping(value = "/tires/{tireId}/dots")
    @ApiOperation(value = "타이어 하위 재고 목록 조회", notes = "입력한 타이어에 해당하는 재고 목록을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    private ApiResponse<List<StockResponse>> findTireDotStocks(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponse.createOK(stockService.findAllByTireId(tireId)
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }
}
