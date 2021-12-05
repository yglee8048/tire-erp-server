package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.tire.request.TireDotRequest;
import com.minsoo.co.tireerpserver.model.tire.request.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.tire.request.TireRequest;
import com.minsoo.co.tireerpserver.model.tire.response.TireDotResponse;
import com.minsoo.co.tireerpserver.model.tire.response.TireMemoResponse;
import com.minsoo.co.tireerpserver.model.tire.response.TireResponse;
import com.minsoo.co.tireerpserver.service.tire.TireDotService;
import com.minsoo.co.tireerpserver.service.tire.TireMemoService;
import com.minsoo.co.tireerpserver.service.tire.TireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TireApi {

    private final TireService tireService;
    private final TireDotService tireDotService;
    private final TireMemoService tireMemoService;

    @GetMapping("/tires")
    public ApiResponse<List<TireResponse>> findAllTires() {
        return ApiResponse.OK(tireService.findAll()
                .stream()
                .map(TireResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tires")
    public ApiResponse<TireResponse> createTire(@RequestBody @Valid TireRequest tireRequest) {
        return ApiResponse.CREATED(new TireResponse(tireService.create(tireRequest)));
    }

    @GetMapping("/tires/{tireId}")
    public ApiResponse<TireResponse> findTireById(@PathVariable Long tireId) {
        return ApiResponse.OK(new TireResponse(tireService.findById(tireId)));
    }

    @PutMapping("/tires/{tireId}")
    public ApiResponse<TireResponse> updateTire(@PathVariable Long tireId,
                                                @RequestBody @Valid TireRequest tireRequest) {
        return ApiResponse.OK(new TireResponse(tireService.update(tireId, tireRequest)));
    }

    @DeleteMapping("/tires/{tireId}")
    public ApiResponse<Void> deleteTireById(@PathVariable Long tireId) {
        tireService.deleteById(tireId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("tire-dots")
    public ApiResponse<List<TireDotResponse>> findAllTireDots() {
        return ApiResponse.OK(tireDotService.findAll()
                .stream()
                .map(TireDotResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/tires/{tireId}/tire-dots")
    public ApiResponse<List<TireDotResponse>> findTireDotsByTire(@PathVariable Long tireId) {
        return ApiResponse.OK(tireDotService.findAllByTireId(tireId)
                .stream()
                .map(TireDotResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tires/{tireId}/tire-dots")
    public ApiResponse<TireDotResponse> createTireDot(@PathVariable Long tireId,
                                                      @RequestBody @Valid TireDotRequest tireDotRequest) {
        return ApiResponse.CREATED(new TireDotResponse(tireDotService.create(tireId, tireDotRequest.getDot())));
    }

    @GetMapping("/tires/{tireId}/tire-dots/{tireDotId}")
    public ApiResponse<TireDotResponse> findTireDotById(@PathVariable Long tireId,
                                                        @PathVariable Long tireDotId) {
        return ApiResponse.OK(new TireDotResponse(tireDotService.findById(tireDotId)));
    }

    @PutMapping("/tires/{tireId}/tire-dots/{tireDotId}")
    public ApiResponse<TireDotResponse> updateTireDot(@PathVariable Long tireId,
                                                      @PathVariable Long tireDotId,
                                                      @RequestBody @Valid TireDotRequest tireDotRequest) {
        return ApiResponse.OK(new TireDotResponse(tireDotService.update(tireId, tireDotId, tireDotRequest.getDot())));
    }

    @DeleteMapping("/tires/{tireId}/tire-dots/{tireDotId}")
    public ApiResponse<Void> deleteTireDotById(@PathVariable Long tireId,
                                               @PathVariable Long tireDotId) {
        tireDotService.deleteById(tireDotId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/tires/{tireId}/tire-memos")
    public ApiResponse<List<TireMemoResponse>> findAllTireMemos(@PathVariable Long tireId) {
        return ApiResponse.OK(tireMemoService.findAllByTire(tireId)
                .stream()
                .map(TireMemoResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tires/{tireId}/tire-memos")
    public ApiResponse<TireMemoResponse> createTireMemo(@PathVariable Long tireId,
                                                        @RequestBody @Valid TireMemoRequest tireMemoRequest) {
        return ApiResponse.CREATED(new TireMemoResponse(tireMemoService.create(tireId, tireMemoRequest)));
    }

    @GetMapping("/tires/{tireId}/tire-memos/{tireMemoId}")
    public ApiResponse<TireMemoResponse> findTireMemoById(@PathVariable Long tireId,
                                                          @PathVariable Long tireMemoId) {
        return ApiResponse.OK(new TireMemoResponse(tireMemoService.findById(tireMemoId)));
    }

    @PutMapping("/tires/{tireId}/tire-memos/{tireMemoId}")
    public ApiResponse<TireMemoResponse> updateTireMemo(@PathVariable Long tireId,
                                                        @PathVariable Long tireMemoId,
                                                        @RequestBody @Valid TireMemoRequest tireMemoRequest) {
        return ApiResponse.OK(new TireMemoResponse(tireMemoService.update(tireId, tireMemoId, tireMemoRequest)));
    }

    @DeleteMapping("/tires/{tireId}/tire-memos/{tireMemoId}")
    public ApiResponse<Void> deleteTireMemoById(@PathVariable Long tireId,
                                                @PathVariable Long tireMemoId) {
        tireMemoService.deleteById(tireMemoId);
        return ApiResponse.NO_CONTENT();
    }
}
