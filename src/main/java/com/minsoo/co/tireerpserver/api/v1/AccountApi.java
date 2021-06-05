//package com.minsoo.co.tireerpserver.api.v1;
//
//import com.minsoo.co.tireerpserver.auth.JwtFilter;
//import com.minsoo.co.tireerpserver.auth.TokenProvider;
//import com.minsoo.co.tireerpserver.model.dto.account.account.AccountRequest;
//import com.minsoo.co.tireerpserver.model.dto.account.account.TokenResponse;
//import com.minsoo.co.tireerpserver.api.v1.model.ApiResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api/v1")
//@RequiredArgsConstructor
//public class AccountApi {
//
//    private final TokenProvider tokenProvider;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<ApiResponse<TokenResponse>> authorize(@Valid @RequestBody AccountRequest accountRequest) {
//
//        UsernamePasswordAuthenticationToken authenticationToken
//                = new UsernamePasswordAuthenticationToken(accountRequest.getUserId(), accountRequest.getUserPw());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenProvider.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//
//        return ApiResponse.of(HttpStatus.CREATED, httpHeaders, "요청이 성공하였습니다.", new TokenResponse(jwt));
//    }
//}
