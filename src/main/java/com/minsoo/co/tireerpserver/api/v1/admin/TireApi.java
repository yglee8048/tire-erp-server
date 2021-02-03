package com.minsoo.co.tireerpserver.api.v1.admin;

import com.minsoo.co.tireerpserver.model.dto.ResponseDTO;
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
    public ResponseDTO<List<TireResponse>> findAllTires() {
        return new ResponseDTO<>(tireService.findAll()
                .stream()
                .map(TireResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}")
    public ResponseDTO<TireResponse> findTireById(@PathVariable Long tireId) {
        return new ResponseDTO<>(new TireResponse(tireService.findById(tireId)));
    }

    @PostMapping
    public ResponseDTO<TireResponse> createTire(@RequestBody @Valid TireCreateRequest createRequest) {
        return new ResponseDTO<>(new TireResponse(tireService.create(createRequest)));
    }

    @PutMapping
    public ResponseDTO<TireResponse> updateTire(@RequestBody @Valid TireUpdateRequest updateRequest) {
        return new ResponseDTO<>(new TireResponse(tireService.update(updateRequest)));
    }

    @DeleteMapping(value = "/{tireId}")
    public ResponseDTO<Long> deleteTire(@PathVariable Long tireId) {
        tireService.remove(tireId);
        return new ResponseDTO<>(tireId);
    }

    // TIRE DOT

    // TIRE DOT MEMO
    @GetMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos")
    public ResponseDTO<List<TireDotMemoResponse>> findAllTireDotMemos(@PathVariable(value = "tireId") Long tireId,
                                                                      @PathVariable(value = "tireDotId") Long tireDotId) {
        return new ResponseDTO<>(tireDotMemoService.findAllByTireDotId(tireDotId)
                .stream()
                .map(TireDotMemoResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos/{tireDotMemoId}")
    public ResponseDTO<TireDotMemoResponse> findTireDotMemoById(@PathVariable(value = "tireId") Long tireId,
                                                                @PathVariable(value = "tireDotId") Long tireDotId,
                                                                @PathVariable(value = "tireDotMemoId") Long tireDotMemoId) {
        return new ResponseDTO<>(new TireDotMemoResponse(tireDotMemoService.findById(tireDotMemoId)));
    }

    @PostMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos")
    public ResponseDTO<TireDotMemoResponse> createTireDotMemo(@PathVariable(value = "tireId") Long tireId,
                                                              @PathVariable(value = "tireDotId") Long tireDotId,
                                                              @RequestBody @Valid TireDotMemoCreateRequest createRequest) {
        return new ResponseDTO<>(new TireDotMemoResponse(tireDotMemoService.create(tireDotId, createRequest)));
    }

    @PostMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos/{tireDotMemoId}/lock")
    public ResponseDTO<TireDotMemoResponse> updateTireDotMemoLock(@PathVariable(value = "tireId") Long tireId,
                                                                  @PathVariable(value = "tireDotId") Long tireDotId,
                                                                  @PathVariable(value = "tireDotMemoId") Long tireDotMemoId,
                                                                  @RequestParam(value = "lock") boolean lock) {
        return new ResponseDTO<>(new TireDotMemoResponse(tireDotMemoService.updateLock(tireDotMemoId, lock)));
    }

    @PatchMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos/{tireDotMemoId}")
    public ResponseDTO<TireDotMemoResponse> updateTireDotMemoMemo(@PathVariable(value = "tireId") Long tireId,
                                                                  @PathVariable(value = "tireDotId") Long tireDotId,
                                                                  @PathVariable(value = "tireDotMemoId") Long tireDotMemoId,
                                                                  @RequestParam(value = "memo") String memo) {
        return new ResponseDTO<>(new TireDotMemoResponse(tireDotMemoService.updateMemo(tireDotMemoId, memo)));
    }

    @DeleteMapping(value = "/{tireId}/tire-dots/{tireDotId}/tire-dot-memos/{tireDotMemoId}")
    public ResponseDTO<Long> deleteTireDotMemo(@PathVariable(value = "tireId") Long tireId,
                                               @PathVariable(value = "tireDotId") Long tireDotId,
                                               @PathVariable(value = "tireDotMemoId") Long tireDotMemoId) {
        tireDotMemoService.remove(tireDotMemoId);
        return new ResponseDTO<>(tireDotMemoId);
    }
}
