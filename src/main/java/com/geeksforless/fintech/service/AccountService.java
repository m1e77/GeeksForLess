package com.geeksforless.fintech.service;

import com.geeksforless.fintech.exception.InsufficientFundsException;
import com.geeksforless.fintech.exception.InvalidTransferAmountException;
import com.geeksforless.fintech.exception.NotFoundException;
import com.geeksforless.fintech.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Represents the core service interface for account-related operations.
 * <p>
 * The service provides capabilities to:
 * <ul>
 *     <li>Transfer funds between two accounts</li>
 *     <li>Retrieve account details by ID</li>
 * </ul>
 * </p>
 */
public interface AccountService {

    /**
     * Transfers a specified amount between two accounts.
     * <p>
     * The method ensures that:
     * - The amount to be transferred is positive
     * - The source account has enough funds for the transfer
     * - Both the source and destination accounts exist
     * </p>
     *
     * @param fromAccountId The ID of the account from which the amount will be debited.
     * @param toAccountId   The ID of the account to which the amount will be credited.
     * @param amount        The amount to be transferred. Must be positive.
     * @throws InvalidTransferAmountException If the specified transfer amount is negative.
     * @throws NotFoundException             If either the source or destination account does not exist.
     * @throws InsufficientFundsException    If the source account does not have enough funds for the transfer.
     */
    void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount);

    /**
     * Retrieves the details of an account based on its ID.
     *
     * <p>
     * If the account with the specified ID does not exist, this method returns {@code null}.
     * </p>
     *
     * @param accountId The ID of the account to be retrieved.
     * @return The account associated with the specified ID or Optional.empty() if no account is found.
     */
    Optional<Account> get(Long accountId);

    /**
     * Retrieves all the accounts from the data source.
     *
     * @return A list of {@link Account} objects representing all accounts.
     *         The list can be empty if no accounts are found.
     */
    List<Account> getAll();
}
