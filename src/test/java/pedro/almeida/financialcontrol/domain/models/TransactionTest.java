package pedro.almeida.financialcontrol.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.errors.TransactionException;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test()
    @DisplayName("Deve lançar uma exceção quando um valor menor ou igual a zero é fornecido a transação")
    void shouldThrowAnExceptionWhenAValueLessThanOrEqualToZeroIsProvided() {
        assertThrows(TransactionException.class, () -> new Transaction("Title", new BigDecimal("-1"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now()));
        assertThrows(TransactionException.class, () -> new Transaction("Title", new BigDecimal("0"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now()));
        assertThrows(TransactionException.class, () -> new Transaction("Title", "", new BigDecimal("-1"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now()));
        assertThrows(TransactionException.class, () -> new Transaction("Title", "", new BigDecimal("0"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now()));
    }

}