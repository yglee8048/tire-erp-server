package com.minsoo.co.tireerpserver.service.account;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.account.Account;
import com.minsoo.co.tireerpserver.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username)
                .map(this::generateUser)
                .orElseThrow(() -> new UsernameNotFoundException(SystemMessage.USER_NAME_NOT_FOUND));
    }

    private User generateUser(Account account) {
        return new User(account.getUsername(), account.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(account.getRole().name())));
    }
}
