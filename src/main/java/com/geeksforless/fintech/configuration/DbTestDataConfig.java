package com.geeksforless.fintech.configuration;

import com.geeksforless.fintech.model.Account;
import com.geeksforless.fintech.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DbTestDataConfig {

    private final AccountRepository accountRepository;

    @PostConstruct
    public void addTestData() {
        accountRepository.save(new Account(1L, BigDecimal.valueOf(100.0)));
        accountRepository.save(new Account(2L, BigDecimal.valueOf(200.0)));
    }
}
