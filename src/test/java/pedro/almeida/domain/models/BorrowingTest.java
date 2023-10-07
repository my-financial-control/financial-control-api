package pedro.almeida.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pedro.almeida.domain.errors.ParcelExceedsBorrowingValueException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BorrowingTest {

    @Test
    @DisplayName("Deve adicionar a parcela ao atributo 'parcels' da classe Borrowing")
    void payParcelWhenTheParcelIsNotExceededAndNeitherTheFinalOneTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("200.0")));

        List<ParcelBorrowing> parcels = Arrays.asList(
                new ParcelBorrowing(new BigDecimal("10.0")),
                new ParcelBorrowing(new BigDecimal("20.0")),
                new ParcelBorrowing(new BigDecimal("30.0")),
                new ParcelBorrowing(new BigDecimal("40.0"))
        );

        doReturn(false).when(borrowing).isParcelExceedsBorrowingValue(any());
        doReturn(false).when(borrowing).isBorrowingFullPaid();

        parcels.forEach(borrowing::payParcel);

        BigDecimal parcelsSumExpected = parcels.stream().map(ParcelBorrowing::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal parcelsBorrowingSum = borrowing.getParcels().stream().map(ParcelBorrowing::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(parcelsSumExpected, parcelsBorrowingSum);
    }

    @Test
    @DisplayName("Deve disparar a exceção ParcelExceedsBorrowingValueException")
    void payParcelWithParcelExceededTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("50.0")));
        ParcelBorrowing parcel = new ParcelBorrowing(new BigDecimal("100.0"));
        doReturn(true).when(borrowing).isParcelExceedsBorrowingValue(any());

        assertThrows(ParcelExceedsBorrowingValueException.class, () -> borrowing.payParcel(parcel));
    }

    @Test
    @DisplayName("Deve alterar o valor do atributo 'paid' da classe Borrowing para 'true'")
    void payParcelWhenTheParcelIsNotExceededAndIsTheFinalOneTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("50.0")));
        List<ParcelBorrowing> parcels = Arrays.asList(
                new ParcelBorrowing(new BigDecimal("25.0")),
                new ParcelBorrowing(new BigDecimal("25.0"))
        );
        parcels.forEach(borrowing::payParcel);
        doReturn(false).when(borrowing).isParcelExceedsBorrowingValue(any());

        borrowing.isBorrowingFullPaid();

        assertEquals(borrowing.getPaid(), true);
}

    @Test
    @DisplayName("Deve retornar 'true' quando o valor da parcela exceder o valor do empréstimo")
    void isParcelExceedsBorrowingValueTrueTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("50.0")));
        ParcelBorrowing parcel = new ParcelBorrowing(new BigDecimal("100.0"));

        assertEquals(borrowing.isParcelExceedsBorrowingValue(parcel), true);
    }

    @Test
    @DisplayName("Deve retornar 'false' quando o valor da parcela não exceder o valor do empréstimo")
    void isParcelExceedsBorrowingValueFalseTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("50.0")));
        ParcelBorrowing parcel = new ParcelBorrowing(new BigDecimal("50.0"));

        assertEquals(false, borrowing.isParcelExceedsBorrowingValue(parcel));
    }

    @Test
    @DisplayName("Deve retornar 'true' quando o valor do empréstimo foi totalmente pago")
    void isBorrowingFullPaidTrueTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("50.0")));
        when(borrowing.sumParcels()).thenReturn(new BigDecimal("50.0"));

        assertEquals(true, borrowing.isBorrowingFullPaid());
    }

    @Test
    @DisplayName("Deve retornar 'false' quando o valor do empréstimo não foi totalmente pago")
    void isBorrowingFullPaidFalseTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("50.0")));

        when(borrowing.sumParcels()).thenReturn(new BigDecimal("40.0"));

        assertEquals(false, borrowing.isBorrowingFullPaid());
    }

    @Test
    @DisplayName("Deve retornar zero quando não foi paga nenhuma parcela")
    void sumParcelsShouldReturnZeroTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = new Borrowing(borrower, new BigDecimal("100.0"));

        assertEquals(BigDecimal.ZERO, borrowing.sumParcels());
    }

    @Test
    @DisplayName("Deve retornar a soma das parcelas pagas")
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

        assertEquals(new BigDecimal("80.0"), borrowing.sumParcels());
    }

}