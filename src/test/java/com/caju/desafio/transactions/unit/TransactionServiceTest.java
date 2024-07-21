package com.caju.desafio.transactions.unit;

import com.caju.desafio.application.transactions.CreateTransactionRequest;
import com.caju.desafio.application.transactions.TransactionService;
import com.caju.desafio.domain.accounts.*;
import com.caju.desafio.domain.merchants.IMerchantRepository;
import com.caju.desafio.domain.merchants.Mcc;
import com.caju.desafio.domain.merchants.Merchant;
import com.caju.desafio.domain.merchants.MerchantName;
import com.caju.desafio.domain.transactions.Amount;
import com.caju.desafio.domain.transactions.ITransactionRepository;
import com.caju.desafio.domain.transactions.Transaction;
import com.caju.desafio.domain.transactions.TransactionErrors;
import com.caju.desafio.shared.Error;
import com.caju.desafio.shared.Result;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionServiceTest {

    @InjectMocks
    private static TransactionService transactionService;
    @Mock
    private static ITransactionRepository transactionRepository;
    @Mock
    private static IAccountRepository accountRepository;
    @Mock
    private static IMerchantRepository merchantRepository;

    private AutoCloseable autoCloseable;


    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(accountRepository, transactionRepository, merchantRepository);
    }

    @AfterEach
    void TearDown() throws Exception {
        autoCloseable.close();
    }

    //Validation tests
    @Test
    public void givenNullAccountId_ShouldReturn_AccountIdNotNullFailure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(
                null,
                100d,
                "5411",
                "UBER TRIP                   SAO PAULO BR");

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), TransactionErrors.INVALID_AMOUNT.code());
        assertEquals(result.getError().description(), TransactionErrors.INVALID_ACCOUNT_ID.description());
    }

    @Test
    public void givenZeroAmount_ShouldReturn_AmountCannotBeZeroOrNullFailure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString(
                "51c3d21f-21bf-49a4-8746-3d508b013db9"),
                0d,
                "5411",
                "UBER TRIP                   SAO PAULO BR");

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), TransactionErrors.INVALID_AMOUNT.code());
        assertEquals(result.getError().description(), TransactionErrors.INVALID_AMOUNT.description());
    }

    @Test
    public void givenNegativeAmount_ShouldReturn_AmountCannotBeZeroOrNegativeFailure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString(
                "51c3d21f-21bf-49a4-8746-3d508b013db9"),
                -10d,
                "5411", "UBER TRIP                   SAO PAULO BR");

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), TransactionErrors.INVALID_AMOUNT.code());
        assertEquals(result.getError().description(), TransactionErrors.INVALID_AMOUNT.description());
    }

    @Test
    public void givenNullAmount_ShouldReturn_AmountCannotBeZeroOrNegativeFailure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(
                UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9"),
                null,
                "5411",
                "UBER TRIP                   SAO PAULO BR");

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), TransactionErrors.INVALID_AMOUNT.code());
        assertEquals(result.getError().description(), TransactionErrors.INVALID_AMOUNT.description());
    }

    @Test
    public void givenNullMcc_ShouldReturn_MccCannotBeNullOrEmptyFailure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9"),
                10d,
                null,
                "UBER TRIP                   SAO PAULO BR");

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), TransactionErrors.INVALID_MCC.code());
        assertEquals(result.getError().description(), TransactionErrors.INVALID_MCC.description());
    }

    @Test
    public void givenEmptyMcc_ShouldReturn_MccCannotBeNullOrEmptyFailure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9"),
                10d,
                "",
                "UBER TRIP                   SAO PAULO BR");

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), TransactionErrors.INVALID_MCC.code());
        assertEquals(result.getError().description(), TransactionErrors.INVALID_MCC.description());
    }

    @Test
    public void givenNullMerchant_ShouldReturn_MerchantCannotBeNullOrEmptyFailure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9"),
                10d,
                "5411",
                null);

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), TransactionErrors.INVALID_MERCHANT.code());
        assertEquals(result.getError().description(), TransactionErrors.INVALID_MERCHANT.description());
    }

    @Test
    public void givenEmptyMerchant_ShouldReturn_MerchantCannotBeNullOrEmptyFailure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9"),
                10d,
                "5411",
                "");

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), TransactionErrors.INVALID_MERCHANT.code());
        assertEquals(result.getError().description(), TransactionErrors.INVALID_MERCHANT.description());
    }

    //Flow tests
    @Test
    public void givenNonExistentAccountId_ShouldReturn_AccountNotFoundFailure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString("8a8a0ff2-a216-4b17-a752-7f3272dd016b"),
                10d,
                "6000",
                "UBER TRIP                   SAO PAULO BR");

        Transaction transaction = Transaction.createTransaction(
                new AccountId(request.accountId()),
                new Amount(request.totalAmount()),
                new MerchantName(request.merchant()),
                new Mcc(request.mcc()));

        //Mock
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(accountRepository.findById(new AccountId(request.accountId()))).thenReturn(null);

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), AccountErrors.NOT_FOUND.code());
        assertEquals(result.getError().description(), AccountErrors.NOT_FOUND.description());
    }

    @Test
    public void givenEnoughBalanceOnRightType_ShouldReturn_Success() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9"),
                10d,
                "5411",
                "UBER TRIP                   SAO PAULO BR");

        Transaction transaction = Transaction.createTransaction(
                new AccountId(request.accountId()),
                new Amount(request.totalAmount()),
                new MerchantName(request.merchant()),
                new Mcc(request.mcc()));

        Map<BalanceType, Double> balance = new HashMap<>();
        balance.put(BalanceType.FOOD, 11d);
        balance.put(BalanceType.MEAL, 0d);
        balance.put(BalanceType.CASH, 0d);

        Account account = Account.createAccount(balance);

        //Mock
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(accountRepository.findById(new AccountId(request.accountId()))).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), true);
        assertEquals(result.getError().code(), Error.None.code());
        assertEquals(result.getError().description(), Error.None.description());
    }

    @Test
    public void givenNoBalanceOnRightType_ShouldReturn_Failure() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9"),
                15d,
                "5411",
                "UBER TRIP                   SAO PAULO BR");

        Transaction transaction = Transaction.createTransaction(
                new AccountId(request.accountId()),
                new Amount(request.totalAmount()),
                new MerchantName(request.merchant()),
                new Mcc(request.mcc()));

        Map<BalanceType, Double> balance = new HashMap<>();
        balance.put(BalanceType.FOOD, 11d);
        balance.put(BalanceType.MEAL, 0d);
        balance.put(BalanceType.CASH, 0d);

        Account account = Account.createAccount(balance);

        //Mock
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(accountRepository.findById(new AccountId(request.accountId()))).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), false);
        assertEquals(result.getError().code(), AccountErrors.INSUFFICIENT_FUNDS.code());
        assertEquals(result.getError().description(), AccountErrors.INSUFFICIENT_FUNDS.description());
    }

    @Test
    public void givenNoBalanceOnRightType_AndBalanceOnCashTypeFallback_ShouldReturn_Success() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9"),
                15d,
                "5411",
                "UBER TRIP                   SAO PAULO BR");

        Transaction transaction = Transaction.createTransaction(
                new AccountId(request.accountId()),
                new Amount(request.totalAmount()),
                new MerchantName(request.merchant()),
                new Mcc(request.mcc()));

        Map<BalanceType, Double> balance = new HashMap<>();
        balance.put(BalanceType.FOOD, 11d);
        balance.put(BalanceType.MEAL, 0d);
        balance.put(BalanceType.CASH, 15d);

        Account account = Account.createAccount(balance);

        //Mock
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(accountRepository.findById(new AccountId(request.accountId()))).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), true);
        assertEquals(result.getError().code(), Error.None.code());
        assertEquals(result.getError().description(), Error.None.description());
    }

    @Test
    public void givenWrongMccFromMerchant_ShouldReplaceWithRightMcc_ShouldReturn_Success() {
        //Given
        CreateTransactionRequest request = new CreateTransactionRequest(UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9"),
                15d,
                "5411",
                "UBER TRIP                   SAO PAULO BR");

        Transaction transaction = Transaction.createTransaction(
                new AccountId(request.accountId()),
                new Amount(request.totalAmount()),
                new MerchantName(request.merchant()),
                new Mcc(request.mcc()));

        Map<BalanceType, Double> balance = new HashMap<>();
        balance.put(BalanceType.FOOD, 15d);
        balance.put(BalanceType.MEAL, 0d);
        balance.put(BalanceType.CASH, 15d);

        Account account = Account.createAccount(balance);

        Merchant merchant = Merchant.createMerchant(new MerchantName("UBER TRIP                   SAO PAULO BR"),
                new Mcc("6000"));

        //Mock
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(merchantRepository.findByName(new MerchantName(request.merchant()))).thenReturn(merchant);
        Mockito.when(accountRepository.findById(new AccountId(request.accountId()))).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        //When
        Result result = transactionService.chargeCard(request);

        //Then
        assertEquals(result.getSuccess(), true);
        assertEquals(result.getError().code(), Error.None.code());
        assertEquals(result.getError().description(), Error.None.description());
    }
}
