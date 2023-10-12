package pedro.almeida.financialcontrol.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.errors.ParcelBorrowingException;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ParcelBorrowingTest {

    @Test()
    @DisplayName("Deve lançar uma exceção quando um valor menor ou igual a zero é fornecido a parcela do empréstimo")
    void shouldThrowAnExceptionWhenAValueLessThanOrEqualToZeroIsProvided() {
        assertThrows(ParcelBorrowingException.class, () -> new ParcelBorrowing(new BigDecimal("0"), LocalDate.now()));
        assertThrows(ParcelBorrowingException.class, () -> new ParcelBorrowing(new BigDecimal("-1"), LocalDate.now()));
        assertThrows(ParcelBorrowingException.class, () -> new ParcelBorrowing(new BigDecimal("0")));
        assertThrows(ParcelBorrowingException.class, () -> new ParcelBorrowing(new BigDecimal("-1")));
    }

}