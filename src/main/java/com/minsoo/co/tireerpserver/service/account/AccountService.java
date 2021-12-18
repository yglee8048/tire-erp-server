package com.minsoo.co.tireerpserver.service.account;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.account.Account;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account login(String username, String password) {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Can not find account by username: {}", username);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [계정]");
        });

        if (account.getPassword().equals(passwordEncoder.encode(password))) {
            return account;
        } else {
            log.error("The password is unmatched: {}", username);
            throw new BadRequestException(SystemMessage.INVALID_PASSWORD);
        }
    }
}
