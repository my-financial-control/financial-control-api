package pedro.almeida.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class BorrowingTest {

    @Test
    void payParcel() {
    }

    @Test
    void isParcelExceedsBorrowingValue() {
    }

    @Test
    void isBorrowingFullPaid() {
    }

    @Test
    @DisplayName("Deve retornar zero quando não foi paga nenhuma parcela.")
    void sumParcelsShouldReturnZeroTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = new Borrowing(borrower, new BigDecimal("100.0"));
        assertEquals(borrowing.sumParcels(), BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Deve retornar a soma das parcelas pagas.")
    void sumParcelsShouldReturnsTheCorrectSum() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = new Borrowing(borrower, new BigDecimal("100.0"));
        List<ParcelBorrowing> parcels = Arrays.asList(
            new ParcelBorrowing(new BigDecimal("50.0")),
            new ParcelBorrowing(new BigDecimal("20.0")),
            new ParcelBorrowing(new BigDecimal("10.0"))
        );

        borrowing = spy(borrowing);
        doReturn(parcels).when(borrowing).getParcels();

        assertEquals(borrowing.sumParcels(), new BigDecimal("80.0"));
    }

}