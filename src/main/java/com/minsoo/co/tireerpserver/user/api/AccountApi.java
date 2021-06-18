package com.minsoo.co.tireerpserver.user.api;

import com.minsoo.co.tireerpserver.user.repository.AccountRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class AccountApi {

    private AccountRepository accountRepository;

    @GetMapping(value = "/accounts/duplicate")
    @Operation(summary = "계정 아이디 중복 확인 API")
    public boolean existByUserId(@RequestParam(name = "user_id") String userId) {
        return accountRepository.existsByUsername(userId);
    }
}
