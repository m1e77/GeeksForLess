package com.geeksforless.fintech.controller;

import com.geeksforless.fintech.dto.AccountDetailsDto;
import com.geeksforless.fintech.dto.MoneyTransferDto;
import com.geeksforless.fintech.mapper.AccountMapper;
import com.geeksforless.fintech.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@RequestBody @Valid MoneyTransferDto transferDto) {
        accountService.transfer(transferDto.getFromAccountId(), transferDto.getToAccountId(), transferDto.getAmount());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsDto> getAccount(@PathVariable Long id) {
        return accountService.get(id)
                .map(accountMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AccountDetailsDto>> getAccounts() {
        return ResponseEntity.ok(
                accountService.getAll()
                        .stream()
                        .map(accountMapper::toDto)
                        .toList()
        );
    }
}
