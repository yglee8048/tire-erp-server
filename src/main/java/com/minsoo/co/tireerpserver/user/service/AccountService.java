package com.minsoo.co.tireerpserver.user.service;

import com.minsoo.co.tireerpserver.user.entity.Account;
import com.minsoo.co.tireerpserver.user.repository.AccountRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.user.model.account.AccountRequest;
import com.minsoo.co.tireerpserver.shared.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
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

        Account account = Account.of(accountRequest, passwordEncoder);
        return accountRepository.save(account);
    }
}
