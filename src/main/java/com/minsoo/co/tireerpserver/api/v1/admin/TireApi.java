package com.minsoo.co.tireerpserver.api.v1.admin;

import com.minsoo.co.tireerpserver.model.dto.response.ApiResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.dot.memo.TireDotMemoCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.dot.memo.TireDotMemoResponse;
import com.minsoo.co.tireerpserver.model.dto.management.tire.TireCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.dto.management.tire.TireUpdateRequest;
import com.minsoo.co.tireerpserver.service.TireDotMemoService;
import com.minsoo.co.tireerpserver.service.TireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/admin/api/v1/tires")
@RequiredArgsConstructor
public class TireApi {

    private final TireService tireService;
    private final TireDotMemoService tireDotMemoService;

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

    @PostMapping
    public ApiResponseDTO<TireResponse> createTire(@RequestBody @Valid TireCreateRequest createRequest) {
        return ApiResponseDTO.createOK(TireResponse.of(tireService.create(createRequest)));
    }

    @PutMapping
    public ApiResponseDTO<TireResponse> updateTire(@RequestBody @Valid TireUpdateRequest updateRequest) {
        return ApiResponseDTO.createOK(TireResponse.of(tireService.update(updateRequest)));
    }

    @DeleteMapping(value = "/{tireId}")
    public ApiResponseDTO<String> deleteTire(@PathVariable Long tireId) {
        tireService.remove(tireId);
        return ApiResponseDTO.DEFAULT_OK;
    }

    // TIRE DOT

    // TIRE DOT MEMO
    @GetMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos")
    public ApiResponseDTO<List<TireDotMemoResponse>> findAllTireDotMemos(@PathVariable(value = "tireId") Long tireId,
                                                                         @PathVariable(value = "tireDotId") Long tireDotId) {
        return ApiResponseDTO.createOK(tireDotMemoService.findAllByTireDotId(tireDotId)
                .stream()
                .map(TireDotMemoResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos/{tireDotMemoId}")
    public ApiResponseDTO<TireDotMemoResponse> findTireDotMemoById(@PathVariable(value = "tireId") Long tireId,
                                                                   @PathVariable(value = "tireDotId") Long tireDotId,
                                                                   @PathVariable(value = "tireDotMemoId") Long tireDotMemoId) {
        return ApiResponseDTO.createOK(TireDotMemoResponse.of(tireDotMemoService.findById(tireDotMemoId)));
    }

    @PostMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos")
    public ApiResponseDTO<TireDotMemoResponse> createTireDotMemo(@PathVariable(value = "tireId") Long tireId,
                                                                 @PathVariable(value = "tireDotId") Long tireDotId,
                                                                 @RequestBody @Valid TireDotMemoCreateRequest createRequest) {
        return ApiResponseDTO.createOK(TireDotMemoResponse.of(tireDotMemoService.create(tireDotId, createRequest)));
    }

    @PostMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos/{tireDotMemoId}/lock")
    public ApiResponseDTO<TireDotMemoResponse> updateTireDotMemoLock(@PathVariable(value = "tireId") Long tireId,
                                                                     @PathVariable(value = "tireDotId") Long tireDotId,
                                                                     @PathVariable(value = "tireDotMemoId") Long tireDotMemoId,
                                                                     @RequestParam(value = "lock") boolean lock) {
        return ApiResponseDTO.createOK(TireDotMemoResponse.of(tireDotMemoService.updateLock(tireDotMemoId, lock)));
    }

    @PatchMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos/{tireDotMemoId}")
    public ApiResponseDTO<TireDotMemoResponse> updateTireDotMemoMemo(@PathVariable(value = "tireId") Long tireId,
                                                                     @PathVariable(value = "tireDotId") Long tireDotId,
                                                                     @PathVariable(value = "tireDotMemoId") Long tireDotMemoId,
                                                                     @RequestParam(value = "memo") String memo) {
        return ApiResponseDTO.createOK(TireDotMemoResponse.of(tireDotMemoService.updateMemo(tireDotMemoId, memo)));
    }

    @DeleteMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos/{tireDotMemoId}")
    public ApiResponseDTO<String> deleteTireDotMemo(@PathVariable(value = "tireId") Long tireId,
                                                    @PathVariable(value = "tireDotId") Long tireDotId,
                                                    @PathVariable(value = "tireDotMemoId") Long tireDotMemoId) {
        tireDotMemoService.remove(tireDotMemoId);
        return ApiResponseDTO.DEFAULT_OK;
    }
}
