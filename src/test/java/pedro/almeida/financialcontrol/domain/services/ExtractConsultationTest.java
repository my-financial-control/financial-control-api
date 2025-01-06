package pedro.almeida.financialcontrol.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.infra.repositories.inmemory.TransactionsInMemoryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExtractConsultationTest {

    @Mock
    private Transactions transactions;
    private BigDecimal totalSumOfCredits;
    private BigDecimal totalSumOfCreditsByMonth;
    private BigDecimal totalSumOfExpenses;
    private BigDecimal totalSumOfExpensesByMonth;
    @Mock
    private Borrowings borrowings;
    private BigDecimal totalSumOfRemainingPayment;
    private BigDecimal totalSumOfRemainingPaymentByMonth;
    private final Month byMonth = Month.JANUARY;
    private final int byYear = 2023;
    @InjectMocks
    private ExtractConsultation extractConsultation;

    @BeforeEach
    public void setUp() {
        setUpTransactions();
        setUpBorrowings();
    }

    private void setUpTransactions() {
        TransactionsInMemoryRepository repository = new TransactionsInMemoryRepository();
        List<Transaction> transactionsMock = repository.findAll();
        totalSumOfCredits = transactionsMock.stream().filter(transaction1 -> transaction1.getType().equals(TransactionType.CREDIT)).map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        totalSumOfExpenses = transactionsMock.stream().filter(transaction -> transaction.getType().equals(TransactionType.EXPENSE)).map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        totalSumOfCreditsByMonth = transactionsMock.stream().filter(transaction -> transaction.getType().equals(TransactionType.CREDIT) && transaction.getCurrentMonth().equals(byMonth) && transaction.getDate().getYear() == byYear).map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        totalSumOfExpensesByMonth = transactionsMock.stream().filter(transaction -> transaction.getType().equals(TransactionType.EXPENSE) && transaction.getCurrentMonth().equals(byMonth) && transaction.getDate().getYear() == byYear).map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void setUpBorrowings() {
        List<Borrowing> borrowingsMock = getBorrowings();
        totalSumOfRemainingPayment = borrowingsMock.stream().map(Borrowing::remainingPaymentAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        totalSumOfRemainingPaymentByMonth = borrowingsMock.stream().filter(borrowing -> borrowing.getDate().getMonth().equals(byMonth) && borrowing.getDate().getYear() == byYear).map(Borrowing::remainingPaymentAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static List<Borrowing> getBorrowings() {
        return Arrays.asList(
                new Borrowing(new Borrower("Borrower"), new BigDecimal("20.96"), LocalDate.of(2023, Month.JANUARY, 1)),
                new Borrowing(new Borrower("Borrower"), new BigDecimal("77.00"), LocalDate.of(2023, Month.JANUARY, 1)),
                new Borrowing(new Borrower("Borrower"), new BigDecimal("89.05"), LocalDate.of(2023, Month.FEBRUARY, 1)), new Borrowing(new Borrower("Borrower"), new BigDecimal("20.96"), LocalDate.of(2023, Month.JANUARY, 1)),
                new Borrowing(new Borrower("Borrower"), new BigDecimal("55.00"), LocalDate.of(2023, Month.FEBRUARY, 1)),
                new Borrowing(new Borrower("Borrower"), new BigDecimal("41.04"), LocalDate.of(2023, Month.MARCH, 1)), new Borrowing(new Borrower("Borrower"), new BigDecimal("20.96"), LocalDate.of(2023, Month.JANUARY, 1)),
                new Borrowing(new Borrower("Borrower"), new BigDecimal("30.76"), LocalDate.of(2023, Month.MARCH, 1)),
                new Borrowing(new Borrower("Borrower"), new BigDecimal("90.01"), LocalDate.of(2023, Month.APRIL, 1)),
                new Borrowing(new Borrower("Borrower"), new BigDecimal("47.10"), LocalDate.of(2023, Month.DECEMBER, 1))
        );
    }

    @Test
    void checkBalanceShouldReturnTheBalanceTest() {
        when(transactions.sumOfCredits()).thenReturn(totalSumOfCredits);
        when(transactions.sumOfExpenses()).thenReturn(totalSumOfExpenses);
        when(borrowings.sumOfRemainingPayment()).thenReturn(totalSumOfRemainingPayment);

        BigDecimal balance = extractConsultation.checkBalance();

        BigDecimal balanceExpected = totalSumOfCredits.subtract(totalSumOfExpenses).subtract(totalSumOfRemainingPayment);
        assertEquals(balanceExpected, balance);
        verify(transactions).sumOfCredits();
        verify(transactions).sumOfExpenses();
        verify(borrowings).sumOfRemainingPayment();
    }

    @Test
    void checkBalanceShouldReturnTheBalanceByMonthTest() {
        when(transactions.sumOfCredits(byMonth, byYear)).thenReturn(totalSumOfCreditsByMonth);
        when(transactions.sumOfExpenses(byMonth, byYear)).thenReturn(totalSumOfExpensesByMonth);
        when(borrowings.sumOfRemainingPayment(byMonth, byYear)).thenReturn(totalSumOfRemainingPaymentByMonth);

        BigDecimal balance = extractConsultation.checkBalance(byMonth, byYear);
        BigDecimal balanceExpected = totalSumOfCreditsByMonth.subtract(totalSumOfExpensesByMonth).subtract(totalSumOfRemainingPaymentByMonth);

        assertEquals(balanceExpected, balance);
        verify(transactions).sumOfCredits(byMonth, byYear);
        verify(transactions).sumOfExpenses(byMonth, byYear);
        verify(borrowings).sumOfRemainingPayment(byMonth, byYear);
    }

    @Test
    void checkBalancePlusRemainingPaymentShouldReturnTheBalancePlusRemainingPaymentsOfAllThePeriodTest() {
        when(transactions.sumOfCredits()).thenReturn(totalSumOfCredits);
        when(transactions.sumOfExpenses()).thenReturn(totalSumOfExpenses);
        when(borrowings.sumOfRemainingPayment()).thenReturn(totalSumOfRemainingPayment);

        BigDecimal balance = extractConsultation.checkBalancePlusRemainingPayment();

        BigDecimal balanceExpected = totalSumOfCredits.subtract(totalSumOfExpenses).add(totalSumOfRemainingPayment);
        assertEquals(balanceExpected, balance);
        verify(transactions).sumOfCredits();
        verify(transactions).sumOfExpenses();
        verify(borrowings).sumOfRemainingPayment();
    }

    @Test
    void checkBalancePlusRemainingPaymentShouldReturnTheBalancePlusRemainingPaymentsOfTheProvidedPeriodTest() {
        when(transactions.sumOfCredits(byMonth, byYear)).thenReturn(totalSumOfCreditsByMonth);
        when(transactions.sumOfExpenses(byMonth, byYear)).thenReturn(totalSumOfExpensesByMonth);
        when(borrowings.sumOfRemainingPayment(byMonth, byYear)).thenReturn(totalSumOfRemainingPaymentByMonth);

        BigDecimal balance = extractConsultation.checkBalancePlusRemainingPayment(byMonth, byYear);

        BigDecimal balanceExpected = totalSumOfCreditsByMonth.subtract(totalSumOfExpensesByMonth).add(totalSumOfRemainingPaymentByMonth);
        assertEquals(balanceExpected, balance);
        verify(transactions).sumOfCredits(byMonth, byYear);
        verify(transactions).sumOfExpenses(byMonth, byYear);
        verify(borrowings).sumOfRemainingPayment(byMonth, byYear);
    }
}
