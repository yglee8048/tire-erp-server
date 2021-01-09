package com.minsoo.co.tireerpserver.api.v1.admin;

import com.minsoo.co.tireerpserver.model.dto.ResponseDTO;
import com.minsoo.co.tireerpserver.model.dto.tire.TireCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.TireUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.Admin;
import com.minsoo.co.tireerpserver.service.AdminService;
import com.minsoo.co.tireerpserver.service.TireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/admin/api/v1")
@RequiredArgsConstructor
public class TireApi {

    private final AdminService adminService;
    private final TireService tireService;

    private Admin login() {
        return adminService.findById(1L);
    }

    @GetMapping(value = "/tires")
    public ResponseDTO<List<TireResponse>> findAllTires() {
        return new ResponseDTO<>(tireService.findAll().stream()
                .map(TireResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/tires/{tireId}")
    public ResponseDTO<TireResponse> findTireById(@PathVariable Long tireId) {
        return new ResponseDTO<>(new TireResponse(tireService.findById(tireId)));
    }

    @PostMapping(value = "/tires")
    public ResponseDTO<TireResponse> createTire(@RequestBody TireCreateRequest createRequest) {
        return new ResponseDTO<>(new TireResponse(tireService.create(createRequest, login())));
    }

    @PutMapping(value = "/tires")
    public ResponseDTO<TireResponse> updateTire(@RequestBody TireUpdateRequest updateRequest) {
        return new ResponseDTO<>(new TireResponse(tireService.update(updateRequest, login())));
    }

    @DeleteMapping(value = "/tires/{tireId}")
    public void deleteTire(@PathVariable Long tireId) {
        tireService.remove(tireId);
    }
}
