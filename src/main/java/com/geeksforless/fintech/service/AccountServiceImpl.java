package com.geeksforless.fintech.service;

import com.geeksforless.fintech.exception.InsufficientFundsException;
import com.geeksforless.fintech.exception.InvalidTransferAmountException;
import com.geeksforless.fintech.exception.NotFoundException;
import com.geeksforless.fintech.exception.ServerIsOverloadedException;
import com.geeksforless.fintech.model.Account;
import com.geeksforless.fintech.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.NestedRuntimeException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

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
     * @throws NotFoundException              If either the source or destination account does not exist.
     * @throws InsufficientFundsException     If the source account does not have enough funds for the transfer.
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(
            value = {NestedRuntimeException.class},
            maxAttemptsExpression = "${retry.config.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.config.delay}")
    )
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {

        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new InvalidTransferAmountException("Amount of the transfer must be positive.");
        }

        Account fromAccount = findOrThrow(fromAccountId);
        Account toAccount = findOrThrow(toAccountId);

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough funds in the account to transfer");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    /**
     * Retrieves the details of an account based on its ID.
     *
     * <p>
     * If the account with the specified ID does not exist, this method returns Optional.empty().
     * </p>
     *
     * @param accountId The ID of the account to be retrieved.
     * @return The account associated with the specified ID or {@code null} if no account is found.
     */
    @Override
    public Optional<Account> get(Long accountId) {
        return accountRepository.findById(accountId);
    }

    /**
     * Retrieves all the accounts from the data source.
     *
     * @return A list of {@link Account} objects representing all accounts.
     *         The list can be empty if no accounts are found.
     */
    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    /**
     * Recovery method to handle exceptions that occurred after all retry attempts have been exhausted.
     * <p>
     * This method is invoked by the {@code @Retryable} mechanism after it has exhausted all
     * the retry attempts specified. The primary purpose is to provide a fallback mechanism
     * or an alternative action when the retries fail.
     * </p>
     *
     * @param e the exception that was thrown during the last retry attempt. This parameter allows
     *          the recovery method to act based on the nature of the exception or to log it for
     *          diagnostics.
     * @throws ServerIsOverloadedException indicating that the server is currently overloaded
     *                                     and suggesting the client to try again later.
     */
    @Recover
    public void recover(NestedRuntimeException e) {
        throw new ServerIsOverloadedException("Server is overloaded. Please try again later.");
    }

    /**
     * Retrieves an {@link Account} by its ID from the repository. If the account
     * is not found, it throws a {@link NotFoundException}.
     *
     * @param toAccountId The ID of the account to retrieve.
     * @return The retrieved account.
     * @throws NotFoundException if the account with the specified ID is not found.
     */
    private Account findOrThrow(Long toAccountId) {
        return accountRepository.findById(toAccountId)
                .orElseThrow(() -> new NotFoundException("Account with ID " + toAccountId + " not found"));
    }
}
