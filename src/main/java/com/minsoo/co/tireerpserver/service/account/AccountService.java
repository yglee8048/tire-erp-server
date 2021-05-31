package com.minsoo.co.tireerpserver.service.account;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.model.code.AccountRole;
import com.minsoo.co.tireerpserver.model.dto.account.account.AccountRequest;
import com.minsoo.co.tireerpserver.model.entity.account.Account;
import com.minsoo.co.tireerpserver.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(account.getUserId(), account.getUserPw(), authorities(account.getRoles()));
    }

    private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream()
                .map(accountRole -> new SimpleGrantedAuthority("ROLE_" + accountRole.name()))
                .collect(Collectors.toSet());
    }

    public Account signup(AccountRequest accountRequest) {
        if (accountRepository.existsByUserId(accountRequest.getUserId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }

        Account account = Account.of(accountRequest.getUserId(), passwordEncoder.encode(accountRequest.getUserPw()));
        return accountRepository.save(account);
    }
}
