package com.geeksforless.fintech.mapper;

import com.geeksforless.fintech.dto.AccountDetailsDto;
import com.geeksforless.fintech.model.Account;
import org.springframework.stereotype.Component;

/**
 * Provides methods to map between {@link Account} entities and {@link AccountDetailsDto} data transfer objects.
 * This mapper is used for converting the domain model to DTOs and vice versa, aiding in data transfer
 * between different layers or systems.
 */
@Component
public class AccountMapper {

    /**
     * Converts the provided {@link AccountDetailsDto} into an {@link Account} entity.
     *
     * @param dto The data transfer object to convert. If {@code null}, the method returns {@code null}.
     * @return An {@link Account} entity corresponding to the provided DTO, or {@code null} if the DTO is {@code null}.
     */
    public Account toEntity(AccountDetailsDto dto) {
        if (dto == null) {
            return null;
        }

        Account account = new Account();
        account.setId(dto.getId());
        account.setBalance(dto.getBalance());
        return account;
    }

    /**
     * Converts the provided {@link Account} entity into an {@link AccountDetailsDto}.
     *
     * @param account The account entity to convert. If {@code null}, the method returns {@code null}.
     * @return An {@link AccountDetailsDto} corresponding to the provided account entity,
     *         or {@code null} if the account entity is {@code null}.
     */
    public AccountDetailsDto toDto(Account account) {
        if (account == null) {
            return null;
        }

        AccountDetailsDto dto = new AccountDetailsDto();
        dto.setId(account.getId());
        dto.setBalance(account.getBalance());
        return dto;
    }
}