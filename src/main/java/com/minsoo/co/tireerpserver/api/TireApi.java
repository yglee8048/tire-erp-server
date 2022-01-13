package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.tire.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.request.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireMemoResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireResponse;
import com.minsoo.co.tireerpserver.service.tire.TireDotService;
import com.minsoo.co.tireerpserver.service.tire.TireMemoService;
import com.minsoo.co.tireerpserver.service.tire.TireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TireApi {

    private final TireService tireService;
    private final TireDotService tireDotService;
    private final TireMemoService tireMemoService;

    @GetMapping("/tire-grids")
    public ApiResponse<List<TireGridResponse>> findAllTireGrids() {
        return ApiResponse.OK(tireService.findAllTireGrids());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tires")
    public ApiResponse<TireResponse> createTire(@RequestBody @Valid TireRequest tireRequest) {
        return ApiResponse.CREATED(tireService.create(tireRequest));
    }

    @GetMapping("/tires/{tireId}")
    public ApiResponse<TireResponse> findTireById(@PathVariable Long tireId) {
        return ApiResponse.OK(tireService.findById(tireId));
    }

    @PutMapping("/tires/{tireId}")
    public ApiResponse<TireResponse> updateTire(@PathVariable Long tireId,
                                                @RequestBody @Valid TireRequest tireRequest) {
        return ApiResponse.OK(tireService.update(tireId, tireRequest));
    }

    @DeleteMapping("/tires/{tireId}")
    public ApiResponse<Void> deleteTireById(@PathVariable Long tireId) {
        tireService.deleteById(tireId);
        return ApiResponse.NO_CONTENT();
    }

    @GetMapping("/tires/{tireId}/tire-dot-grids")
    public ApiResponse<List<TireDotGridResponse>> findTireDotGridsByTireId(@PathVariable Long tireId,
                                                                           @RequestParam(required = false) Long clientCompanyId) {
        return ApiResponse.OK(tireDotService.findTireDotGridsByTireIdAndOptionalClientCompanyId(tireId, clientCompanyId));
    }

    @GetMapping("/tires/{tireId}/tire-memos")
    public ApiResponse<List<TireMemoResponse>> findAllTireMemos(@PathVariable Long tireId) {
        return ApiResponse.OK(tireMemoService.findAllByTire(tireId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tires/{tireId}/tire-memos")
    public ApiResponse<TireMemoResponse> createTireMemo(@PathVariable Long tireId,
                                                        @RequestBody @Valid TireMemoRequest tireMemoRequest) {
        return ApiResponse.CREATED(tireMemoService.create(tireId, tireMemoRequest));
    }

    @PutMapping("/tires/{tireId}/tire-memos/{tireMemoId}")
    public ApiResponse<TireMemoResponse> updateTireMemo(@PathVariable Long tireId,
                                                        @PathVariable Long tireMemoId,
                                                        @RequestBody @Valid TireMemoRequest tireMemoRequest) {
        return ApiResponse.OK(tireMemoService.update(tireId, tireMemoId, tireMemoRequest));
    }

    @DeleteMapping("/tires/{tireId}/tire-memos/{tireMemoId}")
    public ApiResponse<Void> deleteTireMemoById(@PathVariable Long tireId,
                                                @PathVariable Long tireMemoId) {
        tireMemoService.deleteById(tireMemoId);
        return ApiResponse.NO_CONTENT();
    }
}
