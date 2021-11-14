package com.minsoo.co.tireerpserver.tire.api;

import com.minsoo.co.tireerpserver.shared.model.ApiResponse;
import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.tire.entity.TireMemo;
import com.minsoo.co.tireerpserver.tire.model.TireRequest;
import com.minsoo.co.tireerpserver.tire.model.TireResponse;
import com.minsoo.co.tireerpserver.tire.model.dot.TireDotRequest;
import com.minsoo.co.tireerpserver.tire.model.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.tire.model.memo.TireMemoRequest;
import com.minsoo.co.tireerpserver.tire.model.memo.TireMemoResponse;
import com.minsoo.co.tireerpserver.tire.service.TireDotService;
import com.minsoo.co.tireerpserver.tire.service.TireMemoService;
import com.minsoo.co.tireerpserver.tire.service.TireService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/admin/api/v1/tires")
@RequiredArgsConstructor
public class TireApi {

    private final TireService tireService;
    private final TireDotService tireDotService;
    private final TireMemoService tireMemoService;

    // TIRE
    @GetMapping
    @Operation(summary = "타이어 목록 조회")
    public ApiResponse<List<TireResponse>> findAllTires() {
        return ApiResponse.OK(tireService.findAll()
                .stream()
                .map(TireResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}")
    @Operation(summary = "타이어 상세 조회")
    public ApiResponse<TireResponse> findTireById(@PathVariable(name = "tireId") Long tireId) {
        return ApiResponse.OK(TireResponse.of(tireService.findById(tireId)));
    }

    @PostMapping
    @Operation(summary = "타이어 생성")
    public ResponseEntity<ApiResponse<Void>> createTire(@RequestBody @Valid TireRequest createRequest) {
        Tire tire = tireService.create(createRequest);
        return ApiResponse.CREATED(
                linkTo(methodOn(TireApi.class).findTireById(tire.getId())).toUri());
    }

    @PutMapping(value = "/{tireId}")
    @Operation(summary = "타이어 수정")
    public ApiResponse<Void> updateTireById(@PathVariable(name = "tireId") Long tireId,
                                            @RequestBody @Valid TireRequest updateRequest) {
        tireService.update(tireId, updateRequest);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping(value = "/{tireId}")
    @Operation(summary = "타이어 삭제")
    public ApiResponse<Void> deleteTireById(@PathVariable(name = "tireId") Long tireId) {
        tireService.removeById(tireId);
        return ApiResponse.NO_CONTENT;
    }

    // TIRE DOT
    @GetMapping(value = "/{tireId}/tire-dots")
    @Operation(summary = "타이어 DOT 목록 조회")
    public ApiResponse<List<TireDotResponse>> findTireDotsByTireId(@PathVariable Long tireId) {
        return ApiResponse.OK(tireDotService.findAllByTireId(tireId)
                .stream()
                .map(TireDotResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}/tire-dots/{tireDotId}")
    @Operation(summary = "타이어 DOT 상세 조회")
    public ApiResponse<TireDotResponse> findTireDotByIds(@PathVariable Long tireId,
                                                         @PathVariable Long tireDotId) {
        return ApiResponse.OK(TireDotResponse.of(tireDotService.findById(tireDotId)));
    }

    @PostMapping(value = "/{tireId}/tire-dots")
    @Operation(summary = "타이어 DOT 생성")
    public ResponseEntity<ApiResponse<Void>> createTireDot(@PathVariable Long tireId,
                                                           @RequestBody TireDotRequest tireDotRequest) {
        TireDot tireDot = tireDotService.create(tireId, tireDotRequest);
        return ApiResponse.CREATED(
                linkTo(methodOn(TireApi.class).findTireDotByIds(tireId, tireDot.getId())).toUri());
    }

    @PutMapping(value = "/{tireId}/tire-dots/{tireDotId}")
    @Operation(summary = "타이어 DOT 수정")
    public ApiResponse<Void> updateTireDotByIds(@PathVariable Long tireId,
                                                @PathVariable Long tireDotId,
                                                @RequestBody TireDotRequest tireDotRequest) {
        tireDotService.update(tireId, tireDotId, tireDotRequest);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping(value = "/{tireId}/tire-dots/{tireDotId}")
    @Operation(summary = "타이어 DOT 삭제")
    public ApiResponse<Void> deleteTireDotByIds(@PathVariable Long tireId,
                                                @PathVariable Long tireDotId) {
        tireDotService.removeById(tireDotId);
        return ApiResponse.NO_CONTENT;
    }

    // TIRE MEMO
    @GetMapping(value = "/{tireId}/tire-memos")
    @Operation(summary = "타이어 메모 목록 조회")
    public ApiResponse<List<TireMemoResponse>> findAllTireMemos(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponse.OK(tireMemoService.findAllByTireId(tireId)
                .stream()
                .map(TireMemoResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @Operation(summary = "타이어 메모 상세 조회", description = "타이어 메모의 상세 정보를 조회한다.")
    public ApiResponse<TireMemoResponse> findTireMemoById(@PathVariable(value = "tireId") Long tireId,
                                                          @PathVariable(value = "tireMemoId") Long tireMemoId) {
        return ApiResponse.OK(TireMemoResponse.of(tireMemoService.findById(tireMemoId)));
    }

    @PostMapping(value = "/{tireId}/tire-memos")
    @Operation(summary = "타이어 메모 생성", description = "타이어 메모를 생성한다.")
    public ResponseEntity<ApiResponse<Void>> createTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                            @RequestBody @Valid TireMemoRequest createRequest) {
        TireMemo tireMemo = tireMemoService.create(tireId, createRequest);
        return ApiResponse.CREATED(
                linkTo(methodOn(TireApi.class).findTireMemoById(tireId, tireMemo.getId())).toUri());
    }

    @PutMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @Operation(summary = "타이어 메모 수정", description = "타이어 메모를 수정한다.")
    public ApiResponse<Void> updateTireMemo(@PathVariable(value = "tireId") Long tireId,
                                            @PathVariable(value = "tireMemoId") Long tireMemoId,
                                            @RequestBody TireMemoRequest updateRequest) {
        tireMemoService.update(tireId, tireMemoId, updateRequest);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @Operation(summary = "타이어 메모 삭제", description = "타이어 메모를 삭제한다.")
    public ApiResponse<Void> deleteTireMemo(@PathVariable(value = "tireId") Long tireId,
                                            @PathVariable(value = "tireMemoId") Long tireMemoId) {
        tireMemoService.removeById(tireMemoId);
        return ApiResponse.NO_CONTENT;
    }
}
