package com.geeksforless.fintech.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Represents the details required for transferring money between accounts.
 * This DTO (Data Transfer Object) encapsulates the essential fields needed to initiate
 * a money transfer operation between two accounts.
 */
@Data
public class MoneyTransferDto {
    /**
     * The unique identifier of the account from which the money will be debited.
     * This field is mandatory for the money transfer operation.
     */
    @NotNull
    private Long fromAccountId;

    /**
     * The unique identifier of the account to which the money will be credited.
     * This field is mandatory for the money transfer operation.
     */
    @NotNull
    private Long toAccountId;

    /**
     * The amount to be transferred between accounts.
     * This field is mandatory for the money transfer operation.
     */
    @NotNull
    private BigDecimal amount;
}
