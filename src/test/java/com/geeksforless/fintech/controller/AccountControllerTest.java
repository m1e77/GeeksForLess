package com.geeksforless.fintech.controller;

import com.geeksforless.fintech.dto.AccountDetailsDto;
import com.geeksforless.fintech.dto.MoneyTransferDto;
import com.geeksforless.fintech.mapper.AccountMapper;
import com.geeksforless.fintech.model.Account;
import com.geeksforless.fintech.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountController accountController;


    @Test
    void testTransferMoney() {
        // Given
        MoneyTransferDto dto = new MoneyTransferDto();
        dto.setFromAccountId(1L);
        dto.setToAccountId(2L);
        dto.setAmount(BigDecimal.TEN);
        doNothing().when(accountService).transfer(dto.getFromAccountId(), dto.getToAccountId(), dto.getAmount());

        // When
        ResponseEntity<Void> response = accountController.transferMoney(dto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accountService).transfer(dto.getFromAccountId(), dto.getToAccountId(), dto.getAmount());
    }

    @Test
    void testGetAccountFound() {
        // Given
        Account account = new Account();
        AccountDetailsDto dto = new AccountDetailsDto();
        when(accountService.get(1L)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(dto);

        // When
        ResponseEntity<AccountDetailsDto> response = accountController.getAccount(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testGetAccountNotFound() {
        // Given
        when(accountService.get(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<AccountDetailsDto> response = accountController.getAccount(1L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAccounts() {
        // Given
        Account account1 = new Account();
        Account account2 = new Account();
        AccountDetailsDto dto1 = new AccountDetailsDto();
        AccountDetailsDto dto2 = new AccountDetailsDto();
        when(accountService.getAll()).thenReturn(Arrays.asList(account1, account2));
        when(accountMapper.toDto(account1)).thenReturn(dto1);
        when(accountMapper.toDto(account2)).thenReturn(dto2);

        // When
        ResponseEntity<List<AccountDetailsDto>> response = accountController.getAccounts();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(dto1));
        assertTrue(response.getBody().contains(dto2));
    }
}

