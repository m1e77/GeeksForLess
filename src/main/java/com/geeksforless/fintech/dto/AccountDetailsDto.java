package com.geeksforless.fintech.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents the details of an account for data transfer purposes.
 * This DTO (Data Transfer Object) encapsulates the essential fields of an account
 * for transferring data between layers or systems.
 */
@Data
public class AccountDetailsDto {

    /** The unique identifier of the account. */
    private Long id;

    /** The current balance of the account. */
    private BigDecimal balance;
}

