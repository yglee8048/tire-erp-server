package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.api.error.errors.BadRequestException;
import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.stock.MoveStockRequest;
import com.minsoo.co.tireerpserver.model.dto.stock.StockResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockParamResponse;
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

    @GetMapping(value = "/tires")
    @ApiOperation(value = "타이어 재고 목록 조회", notes = "타이어 재고의 목록을 조회한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "size", value = "사이즈 (단면폭/편평비/인치가 연결된 7자리 문자열)"),
            @ApiImplicitParam(name = "brand_name", value = "브랜드 이름"),
            @ApiImplicitParam(name = "pattern", value = "패턴"),
            @ApiImplicitParam(name = "product_id", value = "상품 아이디")})
    private ApiResponseDTO<List<TireStockResponse>> findTireStocks(@RequestParam(value = "size", required = false) String size,
                                                                   @RequestParam(value = "brand_name", required = false) String brandName,
                                                                   @RequestParam(value = "pattern", required = false) String pattern,
                                                                   @RequestParam(value = "product_id", required = false) String productId) {

        // validation
        if (size != null && size.length() != 7) {
            throw new BadRequestException("size 의 입력 형식이 잘못되었습니다.");
        }
        return ApiResponseDTO.createOK(stockService.findTireStocks(size, brandName, pattern, productId));
    }

    @GetMapping(value = "/tires/params")
    @ApiOperation(value = "타이어 재고 검색 대상 조회", notes = "타이어 재고를 검색할 수 있는 대상 목록을 조회한다.")
    private ApiResponseDTO<TireStockParamResponse> findTireStockParams() {
        return ApiResponseDTO.createOK(stockService.findTireStockParams());
    }

    @GetMapping(value = "/tires/{tireId}/dots")
    @ApiOperation(value = "타이어 DOT 재고 목록 조회", notes = "타이어 DOT 의 재고 목록을 조회한다.")
    private ApiResponseDTO<List<StockResponse>> findTireDotStocks(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponseDTO.createOK(stockService.findAllByTireId(tireId)
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }

    @PostMapping(value = "/{stockId}/lock")
    @ApiOperation(value = "타이어 DOT 재고 Lock 수정", notes = "타이어 DOT 재고의 공개 여부를 수정한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "lock", value = "잠금 여부(true = 잠금 / false = 공개)")})
    private ApiResponseDTO<StockResponse> updateStockLock(@PathVariable(value = "stockId") Long stockId,
                                                          @RequestParam(value = "lock") boolean lock) {
        return ApiResponseDTO.createOK(StockResponse.of(stockService.updateStockLock(stockId, lock)));
    }

    @PostMapping(value = "/move")
    @ApiOperation(value = "재고 이동", notes = "타이어 DOT 재고를 이동한다.")
    private ApiResponseDTO<List<StockResponse>> moveStock(@RequestBody @Valid MoveStockRequest moveStockRequest) {
        return ApiResponseDTO.createOK(stockService.moveStock(moveStockRequest)
                .stream()
                .map(StockResponse::of)
                .collect(Collectors.toList()));
    }
}
