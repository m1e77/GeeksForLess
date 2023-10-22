package com.geeksforless.fintech.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.geeksforless.fintech.dto.AccountDetailsDto;
import com.geeksforless.fintech.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountMapperTest {

    private AccountMapper accountMapper;

    @BeforeEach
    public void setUp() {
        accountMapper = new AccountMapper();
    }

    @Test
    void testToEntity_givenDto_returnsAccount() {
        // Given
        AccountDetailsDto dto = new AccountDetailsDto();
        dto.setId(1L);
        dto.setBalance(BigDecimal.TEN);

        // When
        Account account = accountMapper.toEntity(dto);

        // Then
        assertEquals(dto.getId(), account.getId());
        assertEquals(dto.getBalance(), account.getBalance());
    }

    @Test
    void testToEntity_givenNullDto_returnsNull() {
        // Given
        AccountDetailsDto dto = null;

        // When
        Account account = accountMapper.toEntity(dto);

        // Then
        assertNull(account);
    }

    @Test
    void testToDto_givenAccount_returnsDto() {
        // Given
        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.TEN);

        // When
        AccountDetailsDto dto = accountMapper.toDto(account);

        // Then
        assertEquals(account.getId(), dto.getId());
        assertEquals(account.getBalance(), dto.getBalance());
    }

    @Test
    void testToDto_givenNullAccount_returnsNull() {
        // Given
        Account account = null;

        // When
        AccountDetailsDto dto = accountMapper.toDto(account);

        // Then
        assertNull(dto);
    }
}

