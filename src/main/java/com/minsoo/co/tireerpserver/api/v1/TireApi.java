package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.model.response.ApiResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireResponse;
import com.minsoo.co.tireerpserver.service.tire.TireDotService;
import com.minsoo.co.tireerpserver.service.tire.TireMemoService;
import com.minsoo.co.tireerpserver.service.tire.PatternService;
import com.minsoo.co.tireerpserver.service.tire.TireService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class TireApi {

    private final PatternService patternService;
    private final TireService tireService;
    private final TireDotService tireDotService;
    private final TireMemoService tireMemoService;

    // TIRE-PATTERN
    @GetMapping("/patterns")
    public ApiResponse<List<PatternResponse>> findAllPatterns() {
        return ApiResponse.OK(patternService.findAll()
                .stream()
                .map(PatternResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping("/patterns/{patternId}")
    public ApiResponse<PatternResponse> findPatternById(@PathVariable(name = "patternId") Long patternId) {
        return ApiResponse.OK(PatternResponse.of(patternService.findById(patternId)));
    }

    @PostMapping("/patterns")
    public ResponseEntity<ApiResponse<String>> createPattern(@RequestBody PatternRequest patternRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(TireApi.class, findPatternById(patternService.create(patternRequest).getId()))).toUri());
    }

    @PutMapping("/patterns/{patternId}")
    public ApiResponse<String> updatePatternById(@PathVariable(name = "patternId") Long patternId, @RequestBody PatternRequest patternRequest) {
        patternService.update(patternId, patternRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping("/patterns/{patternId}")
    public ApiResponse<String> deletePatternById(@PathVariable(name = "patternId") Long patternId) {
        patternService.removeById(patternId);
        return ApiResponse.OK;
    }

    // TIRE
    @GetMapping("/tires")
    public ApiResponse<List<TireResponse>> findAllTires() {
        return ApiResponse.OK(tireService.findAll()
                .stream()
                .map(TireResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}")
    public ApiResponse<TireResponse> findTireById(@PathVariable(name = "tireId") Long tireId) {
        return ApiResponse.OK(TireResponse.of(tireService.findById(tireId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createTire(@RequestBody @Valid TireRequest createRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(TireApi.class, findTireById(tireService.create(createRequest).getId()))).toUri());
    }

    @PutMapping(value = "/{tireId}")
    public ApiResponse<String> updateTireById(@PathVariable(name = "tireId") Long tireId,
                                              @RequestBody @Valid TireRequest updateRequest) {
        tireService.update(tireId, updateRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/{tireId}")
    public ApiResponse<String> deleteTireById(@PathVariable(name = "tireId") Long tireId) {
        tireService.removeById(tireId);
        return ApiResponse.OK;
    }

    // TIRE DOT
    @GetMapping(value = "/{tireId}/tire-dots")
    public ApiResponse<List<TireDotSimpleResponse>> findTireDotsByTireId(@PathVariable Long tireId) {
        return ApiResponse.OK(tireDotService.findAllByTireId(tireId)
                .stream()
                .map(TireDotSimpleResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}/tire-dots/{tireDotId}")
    public ApiResponse<TireDotResponse> findTireDotByIds(@PathVariable Long tireId,
                                                         @PathVariable Long tireDotId) {
        return ApiResponse.OK(TireDotResponse.of(tireDotService.findById(tireId, tireDotId)));
    }

    @PostMapping(value = "/{tireId}/tire-dots")
    public ResponseEntity<ApiResponse<String>> createTireDot(@PathVariable Long tireId,
                                                             @RequestBody TireDotRequest tireDotRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(TireApi.class, findTireDotByIds(tireId, tireDotService.create(tireId, tireDotRequest).getId()))).toUri());
    }

    @PutMapping(value = "/{tireId}/tire-dots/{tireDotId}")
    public ApiResponse<String> updateTireDotByIds(@PathVariable Long tireId,
                                                  @PathVariable Long tireDotId,
                                                  @RequestBody TireDotRequest tireDotRequest) {
        tireDotService.update(tireId, tireDotId, tireDotRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/{tireId}/tire-dots/{tireDotId}")
    public ApiResponse<String> deleteTireDotByIds(@PathVariable Long tireId,
                                                  @PathVariable Long tireDotId) {
        tireDotService.removeById(tireDotId);
        return ApiResponse.OK;
    }

    // TIRE MEMO
    @GetMapping(value = "/{tireId}/tire-memos")
    @Tag(name = "타이어 메모 목록 조회", description = "타이어 메모 목록을 조회한다.")
    @Parameters({@Parameter(name = "tireId", description = "타이어 ID", example = "201324", required = true)})
    public ApiResponse<List<TireMemoResponse>> findAllTireMemos(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponse.OK(tireMemoService.findAllByTireId(tireId)
                .stream()
                .map(TireMemoResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @Tag(name = "타이어 메모 상세 조회", description = "타이어 메모의 상세 정보를 조회한다.")
    @Parameters({
            @Parameter(name = "tireId", description = "타이어 ID", example = "201324", required = true),
            @Parameter(name = "tireMemoId", description = "타이어 메모 ID", example = "201324", required = true)})
    public ApiResponse<TireMemoResponse> findTireMemoById(@PathVariable(value = "tireId") Long tireId,
                                                          @PathVariable(value = "tireMemoId") Long tireMemoId) {
        return ApiResponse.OK(TireMemoResponse.of(tireMemoService.findById(tireId, tireMemoId)));
    }

    @PostMapping(value = "/{tireId}/tire-memos")
    @Tag(name = "타이어 메모 생성", description = "타이어 메모를 생성한다.")
    @Parameters({@Parameter(name = "tireId", description = "타이어 ID", example = "201324", required = true)})
    public ResponseEntity<ApiResponse<String>> createTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                              @RequestBody @Valid TireMemoRequest createRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(TireApi.class,
                        findTireMemoById(tireId, tireMemoService.create(tireId, createRequest).getId()))).toUri());
    }

    @PutMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @Tag(name = "타이어 메모 수정", description = "타이어 메모를 수정한다.")
    @Parameters({
            @Parameter(name = "tireId", description = "타이어 ID", example = "201324", required = true),
            @Parameter(name = "tireMemoId", description = "타이어 메모 ID", example = "201324", required = true)})
    public ApiResponse<String> updateTireMemo(@PathVariable(value = "tireId") Long tireId,
                                              @PathVariable(value = "tireMemoId") Long tireMemoId,
                                              @RequestBody TireMemoRequest updateRequest) {
        tireMemoService.updateTireMemo(tireId, tireMemoId, updateRequest);
        return ApiResponse.OK;
    }

    @DeleteMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    @Tag(name = "타이어 메모 삭제", description = "타이어 메모를 삭제한다.")
    @Parameters({
            @Parameter(name = "tireId", description = "타이어 ID", example = "201324", required = true),
            @Parameter(name = "tireMemoId", description = "타이어 메모 ID", example = "201324", required = true)})
    public ApiResponse<String> deleteTireMemo(@PathVariable(value = "tireId") Long tireId,
                                              @PathVariable(value = "tireMemoId") Long tireMemoId) {
        tireMemoService.removeById(tireId, tireMemoId);
        return ApiResponse.OK;
    }
}
