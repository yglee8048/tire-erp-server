package com.minsoo.co.tireerpserver.user.service;

import com.minsoo.co.tireerpserver.user.entity.Account;
import com.minsoo.co.tireerpserver.user.repository.AccountRepository;
import com.minsoo.co.tireerpserver.shared.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

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

    public String createPassword() {
        Random random = new Random();
        final String specialCharacters = "~!@#$%^&*";

        String password = RandomStringUtils.random(8, true, true) + specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        return passwordEncoder.encode(password);
    }
}
