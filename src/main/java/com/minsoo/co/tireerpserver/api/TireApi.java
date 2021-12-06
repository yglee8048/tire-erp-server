package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.tire.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.request.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.response.grid.TireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.TireGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireMemoResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireResponse;
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

    @GetMapping("/tire-grids")
    public ApiResponse<List<TireGridResponse>> findAllTireStocks() {
        return ApiResponse.OK(tireService.findAll()
                .stream()
                .map(TireGridResponse::new)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tires")
    public ApiResponse<TireGridResponse> createTire(@RequestBody @Valid TireRequest tireRequest) {
        return ApiResponse.CREATED(new TireGridResponse(tireService.create(tireRequest)));
    }

    @GetMapping("/tires/{tireId}")
    public ApiResponse<TireResponse> findTireById(@PathVariable Long tireId) {
        return ApiResponse.OK(new TireResponse(tireService.findById(tireId)));
    }

    @PutMapping("/tires/{tireId}")
    public ApiResponse<TireGridResponse> updateTire(@PathVariable Long tireId,
                                                    @RequestBody @Valid TireRequest tireRequest) {
        return ApiResponse.OK(new TireGridResponse(tireService.update(tireId, tireRequest)));
    }

    @DeleteMapping("/tires/{tireId}")
    public ApiResponse<Void> deleteTireById(@PathVariable Long tireId) {
        tireService.deleteById(tireId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/tires/{tireId}/tire-dots")
    public ApiResponse<List<TireDotGridResponse>> findTireDotsByTire(@PathVariable Long tireId) {
        return ApiResponse.OK(tireDotService.findAllByTireId(tireId)
                .stream()
                .map(TireDotGridResponse::new)
                .collect(Collectors.toList()));
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
