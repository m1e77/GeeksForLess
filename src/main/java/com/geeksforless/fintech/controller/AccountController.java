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

/**
 * A RESTful controller responsible for handling account-related operations.
 */
@Validated
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    /**
     * Transfers money between accounts.
     *
     * @param transferDto the data transfer object containing transfer details
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@RequestBody @Valid MoneyTransferDto transferDto) {
        accountService.transfer(transferDto.getFromAccountId(), transferDto.getToAccountId(), transferDto.getAmount());
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves the details of a specific account by its ID.
     *
     * @param id the ID of the account to retrieve
     * @return a ResponseEntity containing the account details or a not-found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsDto> getAccount(@PathVariable Long id) {
        return accountService.get(id)
                .map(accountMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves the details of all available accounts.
     *
     * @return a ResponseEntity containing a list of account details
     */
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
