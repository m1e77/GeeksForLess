package com.geeksforless.fintech.service;

import com.geeksforless.fintech.model.Account;
import com.geeksforless.fintech.repository.AccountRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceIntegrationTest {

    /**
     * The number of threads used in the concurrency test.
     */
    private static final int NUM_OF_THREADS = 10;

    /**
     * The executor service responsible for handling concurrent tasks.
     */
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(NUM_OF_THREADS);

    /**
     * The amount transferred in each transaction in the concurrency test.
     */
    private static final BigDecimal SINGLE_TRANSFER_AMOUNT = BigDecimal.valueOf(100);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Tests the concurrent transfer of funds and verifies that the balances are updated correctly.
     *
     * @throws InterruptedException if the test is interrupted.
     */
    @Test
    void concurrentTransferShouldUpdateBalancesCorrectly() throws InterruptedException {
        Account fromAccount = prepareFromAccount();
        Account toAccount = prepareToAccount();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM_OF_THREADS);
        CountDownLatch latch = new CountDownLatch(NUM_OF_THREADS);

        for (int i = 0; i < NUM_OF_THREADS; i++) {
            EXECUTOR.submit(() -> {
                try {
                    cyclicBarrier.await();
                    accountService.transfer(fromAccount.getId(), toAccount.getId(), SINGLE_TRANSFER_AMOUNT);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(15, TimeUnit.SECONDS);

        validateBalances(
                accountRepository.findById(fromAccount.getId()).get(),
                accountRepository.findById(toAccount.getId()).get()
        );
    }

    private Account prepareFromAccount() {
        Account fromAccount = new Account();
        fromAccount.setBalance(SINGLE_TRANSFER_AMOUNT.multiply(BigDecimal.valueOf(NUM_OF_THREADS)));
        return accountRepository.save(fromAccount);
    }

    private Account prepareToAccount() {
        Account toAccount = new Account();
        toAccount.setBalance(BigDecimal.ZERO);
        return accountRepository.save(toAccount);
    }

    private void validateBalances(Account fromAccount, Account toAccount) {
        BigDecimal fromAccountBalance = fromAccount.getBalance();
        BigDecimal toAccountBalance = toAccount.getBalance();
        BigDecimal expectedToAccountAmount = SINGLE_TRANSFER_AMOUNT.multiply(BigDecimal.valueOf(NUM_OF_THREADS));
        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(fromAccountBalance)
        );
        assertEquals(
                0,
                expectedToAccountAmount.compareTo(toAccountBalance)
        );
    }

    @AfterAll
    public static void destroy() {
        EXECUTOR.shutdown();
    }
}

