package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import com.minsoo.co.tireerpserver.service.TireDotService;
import com.minsoo.co.tireerpserver.service.TireMemoService;
import com.minsoo.co.tireerpserver.service.TireService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/tires")
@RequiredArgsConstructor
public class TireApi {

    private final TireService tireService;
    private final TireDotService tireDotService;
    private final TireMemoService tireMemoService;

    // TIRE
    @GetMapping
    @ApiOperation(value = "타이어 목록 조회", notes = "타이어 목록을 조회한다.")
    public ApiResponse<List<TireResponse>> findAllTires() {
        return ApiResponse.createOK(tireService.findAll());
    }

    @GetMapping(value = "/{tireId}")
    @ApiOperation(value = "타이어 상세 조회", notes = "타이어 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponse<TireResponse> findTireById(@PathVariable Long tireId) {
        return ApiResponse.createOK(tireService.findById(tireId));
    }

    @PostMapping
    @ApiOperation(value = "타이어 생성", notes = "타이어를 생성한다.")
    public ApiResponse<TireResponse> createTire(@RequestBody @Valid TireRequest createRequest) {
        return ApiResponse.createOK(tireService.create(createRequest));
    }

    @PutMapping(value = "/{tireId}")
    @ApiOperation(value = "타이어 수정", notes = "타이어를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponse<TireResponse> updateTire(@PathVariable Long tireId,
                                                @RequestBody @Valid TireRequest updateRequest) {
        return ApiResponse.createOK(tireService.update(tireId, updateRequest));
    }

    // TIRE DOT
    @GetMapping(value = "/{tireId}/tire-dots")
    @ApiOperation(value = "타이어 DOT 목록 조회", notes = "타이어 DOT 목록을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponse<List<TireDotSimpleResponse>> findTireDots(@PathVariable Long tireId) {
        return ApiResponse.createOK(tireDotService.findAllByTireId(tireId));
    }

    // TIRE MEMO
    @GetMapping(value = "/{tireId}/tire-memos")
    @ApiOperation(value = "타이어 메모 목록 조회", notes = "타이어 메모 목록을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponse<List<TireMemoResponse>> findAllTireMemos(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponse.createOK(tireMemoService.findAllByTireId(tireId));
    }

    @GetMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @ApiOperation(value = "타이어 메모 상세 조회", notes = "타이어 메모의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true),
            @ApiImplicitParam(name = "tireMemoId", value = "타이어 메모 ID", example = "201324", required = true)})
    public ApiResponse<TireMemoResponse> findTireMemoById(@PathVariable(value = "tireId") Long tireId,
                                                          @PathVariable(value = "tireMemoId") Long tireMemoId) {
        return ApiResponse.createOK(tireMemoService.findById(tireId, tireMemoId));
    }

    @PostMapping(value = "/{tireId}/tire-memos")
    @ApiOperation(value = "타이어 메모 생성", notes = "타이어 메모를 생성한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponse<TireMemoResponse> createTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                        @RequestBody @Valid TireMemoRequest createRequest) {
        return ApiResponse.createOK(tireMemoService.create(tireId, createRequest));
    }

    @PutMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @ApiOperation(value = "타이어 메모 수정", notes = "타이어 메모를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true),
            @ApiImplicitParam(name = "tireMemoId", value = "타이어 메모 ID", example = "201324", required = true)})
    public ApiResponse<TireMemoResponse> updateTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                        @PathVariable(value = "tireMemoId") Long tireMemoId,
                                                        @RequestBody TireMemoRequest updateRequest) {
        return ApiResponse.createOK(tireMemoService.updateTireMemo(tireId, tireMemoId, updateRequest));
    }

    @DeleteMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @ApiOperation(value = "타이어 메모 삭제", notes = "타이어 메모를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true),
            @ApiImplicitParam(name = "tireMemoId", value = "타이어 메모 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteTireMemo(@PathVariable(value = "tireId") Long tireId,
                                              @PathVariable(value = "tireMemoId") Long tireMemoId) {
        tireMemoService.removeById(tireId, tireMemoId);
        return ApiResponse.DEFAULT_OK;
    }
}
