//package com.minsoo.co.tireerpserver.api.v1;
//
//import com.minsoo.co.tireerpserver.model.dto.account.customer.CustomerRequest;
//import com.minsoo.co.tireerpserver.model.dto.account.customer.CustomerResponse;
//import com.minsoo.co.tireerpserver.global.model.ApiResponse;
//import com.minsoo.co.tireerpserver.account.service.CustomerService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/api/v1/customers")
//@RequiredArgsConstructor
//public class CustomerApi {
//
//    private final CustomerService customerService;
//
//    @GetMapping
//    @Operation(summary = "거래처 목록 조회", description = "거래처의 목록을 조회한다.")
//    public ApiResponse<List<CustomerResponse>> findAllCustomers() {
//        return ApiResponse.OK(customerService.findAll()
//                .stream()
//                .map(CustomerResponse::of)
//                .collect(Collectors.toList()));
//    }
//
//    @GetMapping(value = "/{customerId}")
//    @Operation(summary = "거래처 상세 조회", description = "거래처의 상세 정보를 조회한다.")
//    @Parameters({@Parameter(name = "customerId", description = "거래처 ID", example = "201324", required = true)})
//    public ApiResponse<CustomerResponse> findCustomerById(@PathVariable(value = "customerId") Long customerId) {
//        return ApiResponse.OK(CustomerResponse.of(customerService.findById(customerId)));
//    }
//
//    @PostMapping
//    @Operation(summary = "거래처 생성", description = "거래처를 생성한다.")
//    public ResponseEntity<ApiResponse<Object>> createCustomer(@RequestBody @Valid CustomerRequest createRequest) {
//        return ApiResponse.CREATED(
//                linkTo(methodOn(CustomerApi.class).findCustomerById(customerService.create(createRequest).getId())).toUri());
//    }
//
//    @PutMapping(value = "/{customerId}")
//    @Operation(summary = "거래처 수정", description = "거래처를 수정한다.")
//    @Parameters({@Parameter(name = "customerId", description = "거래처 ID", example = "201324", required = true)})
//    public ApiResponse<Object> updateCustomer(@PathVariable(value = "customerId") Long customerId,
//                                              @RequestBody @Valid CustomerRequest updateRequest) {
//        customerService.update(customerId, updateRequest);
//        return ApiResponse.OK;
//    }
//
//    @DeleteMapping(value = "/{customerId}")
//    @Operation(summary = "거래처 삭제", description = "거래처를 삭제한다.")
//    @Parameters({@Parameter(name = "customerId", description = "거래처 ID", example = "201324", required = true)})
//    public ApiResponse<Object> deleteCustomer(@PathVariable(value = "customerId") Long customerId) {
//        customerService.removeById(customerId);
//        return ApiResponse.OK;
//    }
//}
