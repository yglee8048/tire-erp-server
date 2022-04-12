package com.minsoo.co.tireerpserver.api;

import com.minsoo.co.tireerpserver.model.ApiResponse;
import com.minsoo.co.tireerpserver.model.request.account.LoginRequest;
import com.minsoo.co.tireerpserver.model.response.account.TokenResponse;
import com.minsoo.co.tireerpserver.repository.account.AccountRepository;
import com.minsoo.co.tireerpserver.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountApi {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountRepository accountRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/authenticate")
    public ApiResponse<TokenResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        return ApiResponse.OK(new TokenResponse(jwt));
    }

    @GetMapping("/accounts/validate")
    public ApiResponse<Boolean> validateUserName(@RequestParam(name = "user_id") String userId) {
        return ApiResponse.OK(accountRepository.existsByUsername(userId));
    }
}
