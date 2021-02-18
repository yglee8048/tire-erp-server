package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
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
import java.util.stream.Collectors;

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
    public ApiResponseDTO<List<TireResponse>> findAllTires() {
        return ApiResponseDTO.createOK(tireService.findAll()
                .stream()
                .map(TireResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}")
    @ApiOperation(value = "타이어 상세 조회", notes = "타이어 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponseDTO<TireResponse> findTireById(@PathVariable Long tireId) {
        return ApiResponseDTO.createOK(TireResponse.of(tireService.findById(tireId)));
    }

    @PostMapping
    @ApiOperation(value = "타이어 생성", notes = "타이어를 생성한다.")
    public ApiResponseDTO<TireResponse> createTire(@RequestBody @Valid TireRequest createRequest) {
        return ApiResponseDTO.createOK(TireResponse.of(tireService.create(createRequest)));
    }

    @PutMapping(value = "/{tireId}")
    @ApiOperation(value = "타이어 수정", notes = "타이어를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponseDTO<TireResponse> updateTire(@PathVariable Long tireId,
                                                   @RequestBody @Valid TireRequest updateRequest) {
        return ApiResponseDTO.createOK(TireResponse.of(tireService.update(tireId, updateRequest)));
    }

    @DeleteMapping(value = "/{tireId}")
    @ApiOperation(value = "타이어 삭제", notes = "타이어를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponseDTO<String> deleteTire(@PathVariable Long tireId) {
        tireService.remove(tireId);
        return ApiResponseDTO.DEFAULT_OK;
    }

    // TIRE DOT
    @GetMapping(value = "/{tireId}/tire-dots")
    @ApiOperation(value = "타이어 DOT 목록 조회", notes = "타이어 DOT 목록을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponseDTO<List<TireDotResponse>> findTireDots(@PathVariable Long tireId) {
        return ApiResponseDTO.createOK(tireDotService.findAllByTireId(tireId)
                .stream()
                .map(TireDotResponse::of)
                .collect(Collectors.toList()));
    }


    // TIRE MEMO
    @GetMapping(value = "/{tireId}/tire-memos")
    @ApiOperation(value = "타이어 메모 목록 조회", notes = "타이어 메모 목록을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponseDTO<List<TireMemoResponse>> findAllTireMemos(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponseDTO.createOK(tireMemoService.findAllByTireId(tireId)
                .stream()
                .map(TireMemoResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @ApiOperation(value = "타이어 메모 상세 조회", notes = "타이어 메모의 상세 정보를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true),
            @ApiImplicitParam(name = "tireMemoId", value = "타이어 메모 ID", example = "201324", required = true)})
    public ApiResponseDTO<TireMemoResponse> findTireMemoById(@PathVariable(value = "tireId") Long tireId,
                                                             @PathVariable(value = "tireMemoId") Long tireMemoId) {
        return ApiResponseDTO.createOK(TireMemoResponse.of(tireMemoService.findById(tireId, tireMemoId)));
    }

    @PostMapping(value = "/{tireId}/tire-memos")
    @ApiOperation(value = "타이어 메모 생성", notes = "타이어 메모를 생성한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true)})
    public ApiResponseDTO<TireMemoResponse> createTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                           @RequestBody @Valid TireMemoRequest createRequest) {
        return ApiResponseDTO.createOK(TireMemoResponse.of(tireMemoService.create(tireId, createRequest)));
    }

    @PutMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @ApiOperation(value = "타이어 메모 수정", notes = "타이어 메모를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true),
            @ApiImplicitParam(name = "tireMemoId", value = "타이어 메모 ID", example = "201324", required = true)})
    public ApiResponseDTO<TireMemoResponse> updateTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                           @PathVariable(value = "tireMemoId") Long tireMemoId,
                                                           @RequestBody TireMemoRequest updateRequest) {
        return ApiResponseDTO.createOK(TireMemoResponse.of(tireMemoService.updateTireMemo(tireId, tireMemoId, updateRequest)));
    }

    @DeleteMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @ApiOperation(value = "타이어 메모 삭제", notes = "타이어 메모를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tireId", value = "타이어 ID", example = "201324", required = true),
            @ApiImplicitParam(name = "tireMemoId", value = "타이어 메모 ID", example = "201324", required = true)})
    public ApiResponseDTO<String> deleteTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                 @PathVariable(value = "tireMemoId") Long tireMemoId) {
        tireMemoService.remove(tireId, tireMemoId);
        return ApiResponseDTO.DEFAULT_OK;
    }
}
