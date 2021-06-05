package com.minsoo.co.tireerpserver.services.account.service;

import com.minsoo.co.tireerpserver.services.account.entity.Account;
import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.v1.model.dto.account.account.AccountRequest;
import com.minsoo.co.tireerpserver.services.account.repository.AccountRepository;
import com.minsoo.co.tireerpserver.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new PrincipalDetails(account);
    }

    public Account signup(AccountRequest accountRequest) {
        if (accountRepository.existsByUsername(accountRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }

        Account account = Account.of(accountRequest.getUserId(), passwordEncoder.encode(accountRequest.getUserPw()));
        return accountRepository.save(account);
    }
}
