package com.minsoo.co.tireerpserver.api.v1;

import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.management.tire.memo.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.dto.management.tire.memo.TireMemoResponse;
import com.minsoo.co.tireerpserver.model.dto.management.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.dto.management.tire.TireResponse;
import com.minsoo.co.tireerpserver.service.TireMemoService;
import com.minsoo.co.tireerpserver.service.TireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/tires")
@RequiredArgsConstructor
public class TireApi {

    private final TireService tireService;
    private final TireMemoService tireMemoService;

    // TIRE
    @GetMapping
    public ApiResponseDTO<List<TireResponse>> findAllTires() {
        return ApiResponseDTO.createOK(tireService.findAll()
                .stream()
                .map(TireResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}")
    public ApiResponseDTO<TireResponse> findTireById(@PathVariable Long tireId) {
        return ApiResponseDTO.createOK(TireResponse.of(tireService.findById(tireId)));
    }

    // TODO:
    @GetMapping(value = "/{tireId}/tire-dots")
    public ApiResponseDTO<List<String>> findTireDots(@PathVariable Long tireId) {
        return ApiResponseDTO.createOK(new ArrayList<>());
    }

    @PostMapping
    public ApiResponseDTO<TireResponse> createTire(@RequestBody @Valid TireRequest createRequest) {
        return ApiResponseDTO.createOK(TireResponse.of(tireService.create(createRequest)));
    }

    @PutMapping(value = "/{tireId}")
    public ApiResponseDTO<TireResponse> updateTire(@PathVariable Long tireId,
                                                   @RequestBody @Valid TireRequest updateRequest) {
        return ApiResponseDTO.createOK(TireResponse.of(tireService.update(tireId, updateRequest)));
    }

    @DeleteMapping(value = "/{tireId}")
    public ApiResponseDTO<String> deleteTire(@PathVariable Long tireId) {
        tireService.remove(tireId);
        return ApiResponseDTO.DEFAULT_OK;
    }

    // TIRE MEMO
    @GetMapping(value = "/{tireId}/tire-memos")
    public ApiResponseDTO<List<TireMemoResponse>> findAllTireMemos(@PathVariable(value = "tireId") Long tireId) {
        return ApiResponseDTO.createOK(tireMemoService.findAllByTireId(tireId)
                .stream()
                .map(TireMemoResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}/tire-memos/{tireDotMemoId}")
    public ApiResponseDTO<TireMemoResponse> findTireMemoById(@PathVariable(value = "tireId") Long tireId,
                                                             @PathVariable(value = "tireDotMemoId") Long tireDotMemoId) {
        return ApiResponseDTO.createOK(TireMemoResponse.of(tireMemoService.findById(tireDotMemoId)));
    }

    @PostMapping(value = "/{tireId}/tire-memos")
    public ApiResponseDTO<TireMemoResponse> createTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                           @RequestBody @Valid TireMemoRequest createRequest) {
        return ApiResponseDTO.createOK(TireMemoResponse.of(tireMemoService.create(tireId, createRequest)));
    }

    @PutMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    public ApiResponseDTO<TireMemoResponse> updateTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                           @PathVariable(value = "tireMemoId") Long tireMemoId,
                                                           @RequestBody TireMemoRequest updateRequest) {
        return ApiResponseDTO.createOK(TireMemoResponse.of(tireMemoService.updateTireMemo(tireMemoId, updateRequest)));
    }

    @DeleteMapping(value = "/{tireId}/tire-memos/{tireMemoId}")
    public ApiResponseDTO<String> deleteTireMemo(@PathVariable(value = "tireId") Long tireId,
                                                 @PathVariable(value = "tireMemoId") Long tireDotMemoId) {
        tireMemoService.remove(tireDotMemoId);
        return ApiResponseDTO.DEFAULT_OK;
    }
}
