package pedro.almeida.financialcontrol.domain.models;

import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.errors.TransactionException;
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionTest {

    @Test()
    void shouldThrowAnExceptionWhenAValueLessThanOrEqualToZeroIsProvided() {
        assertThrows(TransactionException.class, () -> TransactionFactory.buildTransaction("Title", "", new BigDecimal("-1"), "EXPENSE", 1, LocalDate.now(), new TransactionCategory(UUID.randomUUID(), "Category", "", TransactionType.EXPENSE)));
        assertThrows(TransactionException.class, () -> TransactionFactory.buildTransaction("Title", "", new BigDecimal("0"), "EXPENSE", 1, LocalDate.now(), new TransactionCategory(UUID.randomUUID(), "Category", "", TransactionType.EXPENSE)));
        assertThrows(TransactionException.class, () -> TransactionFactory.buildTransaction("Title", "", new BigDecimal("-1"), "EXPENSE", 1, LocalDate.now(), new TransactionCategory(UUID.randomUUID(), "Category", "", TransactionType.EXPENSE)));
        assertThrows(TransactionException.class, () -> TransactionFactory.buildTransaction("Title", "", new BigDecimal("0"), "EXPENSE", 1, LocalDate.now(), new TransactionCategory(UUID.randomUUID(), "Category", "", TransactionType.EXPENSE)));
    }

    @Test
    void shouldThrowExceptionWhenTransactionIsTypeOfExpenseAndCategoryIsNullOrEmpty() {
        assertThrows(TransactionException.class, () -> TransactionFactory.buildTransaction("Title", "", new BigDecimal("1000"), "EXPENSE", 1, LocalDate.now(), null));
        assertThrows(TransactionException.class, () -> TransactionFactory.buildTransaction("Title", "", new BigDecimal("1000"), "EXPENSE", 1, LocalDate.now(), new TransactionCategory(UUID.randomUUID(), "", "", TransactionType.EXPENSE)));
    }

    @Test
    void shouldNotThrowExceptionWhenTransactionIsTypeOfExpenseAndCategoryIsAValidString() {
        assertInstanceOf(Expense.class, TransactionFactory.buildTransaction("Title", "", new BigDecimal("1000"), "EXPENSE", 1, LocalDate.now(), new TransactionCategory(UUID.randomUUID(), "Category", "", TransactionType.EXPENSE)));
    }
}