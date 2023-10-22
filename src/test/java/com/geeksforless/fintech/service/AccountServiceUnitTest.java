package com.geeksforless.fintech.service;

import com.geeksforless.fintech.exception.InsufficientFundsException;
import com.geeksforless.fintech.exception.InvalidTransferAmountException;
import com.geeksforless.fintech.exception.NotFoundException;
import com.geeksforless.fintech.model.Account;
import com.geeksforless.fintech.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AccountServiceUnitTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void testSuccessfulTransfer() {
        // Given
        Account fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setBalance(new BigDecimal("1000"));

        Account toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(new BigDecimal("500"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        // When
        accountService.transfer(1L, 2L, new BigDecimal("200"));

        // Then
        assertEquals(new BigDecimal("800"), fromAccount.getBalance());
        assertEquals(new BigDecimal("700"), toAccount.getBalance());
    }

    @Test
    void testTransferFromNonExistentAccount() {
        // Given
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            accountService.transfer(1L, 2L, new BigDecimal("200"));
        });
    }

    @Test
    void testTransferToNonExistentAccount() {
        // Given
        Account fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setBalance(new BigDecimal("1000"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            accountService.transfer(1L, 2L, new BigDecimal("200"));
        });
    }

    @Test
    void testTransferWithInsufficientFunds() {
        // Given
        Account fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setBalance(new BigDecimal("100"));

        Account toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(new BigDecimal("500"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        // When & Then
        assertThrows(InsufficientFundsException.class, () -> {
            accountService.transfer(1L, 2L, new BigDecimal("200"));
        });
    }

    @Test
    void testTransferNegativeAmount() {
        // Given, When & Then
        assertThrows(InvalidTransferAmountException.class, () -> {
            accountService.transfer(1L, 2L, new BigDecimal("-100"));
        });
    }

    @Test
    void testTransferZeroAmount() {
        // Given, When & Then
        assertThrows(InvalidTransferAmountException.class, () -> {
            accountService.transfer(1L, 2L, new BigDecimal("0"));
        });
    }

    @Test
    void getAccountDetailsForExistingAccount() {

        //Given
        Account existingAccount = new Account();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(existingAccount));

        //When
        Optional<Account> account = accountService.get(1L);

        //Then
        assertTrue(account.isPresent());
        assertEquals(existingAccount, account.get());
    }

    @Test
    void getAccountDetailsForNotExistingAccount() {

        //Given & When
        Optional<Account> account = accountService.get(1L);

        //Then
        assertFalse(account.isPresent());
    }

    @Test
    void getAllAccounts() {

        // Given
        Account acc1 = new Account();
        Account acc2 = new Account();
        when(accountRepository.findAll()).thenReturn(List.of(acc1, acc2));

        //Given & When
        List<Account> accounts = accountService.getAll();

        //Then
        assertEquals(List.of(acc1, acc2), accounts);
    }
}
