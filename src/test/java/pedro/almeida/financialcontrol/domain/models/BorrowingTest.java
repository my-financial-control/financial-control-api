package pedro.almeida.financialcontrol.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.errors.BorrowingException;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BorrowingTest {

    @Test()
    @DisplayName("Deve lançar uma exceção quando um valor menor ou igual a zero é fornecido ao empréstimo")
    void shouldThrowAnExceptionWhenAValueLessThanOrEqualToZeroIsProvided() {
        assertThrows(BorrowingException.class, () -> new Borrowing(new Borrower("Borrower"), new BigDecimal("0")));
        assertThrows(BorrowingException.class, () -> new Borrowing(new Borrower("Borrower"), new BigDecimal("-1")));
        assertThrows(BorrowingException.class, () -> new Borrowing(new Borrower("Borrower"), new BigDecimal("0"), LocalDate.now()));
        assertThrows(BorrowingException.class, () -> new Borrowing(new Borrower("Borrower"), new BigDecimal("-1"), LocalDate.now()));
    }

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
    @DisplayName("Deve disparar a exceção BorrowingException")
    void payParcelWithParcelExceededTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("50.0")));
        ParcelBorrowing parcel = new ParcelBorrowing(new BigDecimal("100.0"));
        doReturn(true).when(borrowing).isParcelExceedsBorrowingValue(any());

        assertThrows(BorrowingException.class, () -> borrowing.payParcel(parcel));
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
    @DisplayName("Deve retornar 'true' quando o valor total das parcelas for igual ao valor total do empréstimo")
    void isBorrowingFullPaidEqualsValueTrueTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("50.0")));
        when(borrowing.sumParcels()).thenReturn(new BigDecimal("50.0"));

        assertEquals(true, borrowing.isBorrowingFullPaid());
    }

    @Test
    @DisplayName("Deve retornar 'true' quando o valor total das parcelas for maior que o valor total do empréstimo")
    void isBorrowingFullPaidGreaterValueTrueTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = spy(new Borrowing(borrower, new BigDecimal("50.0")));
        when(borrowing.sumParcels()).thenReturn(new BigDecimal("60.0"));

        assertEquals(true, borrowing.isBorrowingFullPaid());
    }

    @Test
    @DisplayName("Deve retornar 'false' quando o valor total das parcelas for menor do que o valor total do empréstimo")
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

    @Test
    @DisplayName("Deve retornar qual o valor restante a pagar do empréstimo")
    void remainingPaymentAmountTest() {
        Borrower borrower = new Borrower("Borrower");
        Borrowing borrowing = new Borrowing(borrower, new BigDecimal("100.0"));

        List<ParcelBorrowing> parcels = Arrays.asList(
                new ParcelBorrowing(new BigDecimal("50.0")),
                new ParcelBorrowing(new BigDecimal("20.0")),
                new ParcelBorrowing(new BigDecimal("10.0"))
        );
        BigDecimal sumParcels = parcels.stream().map(ParcelBorrowing::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);

        borrowing = spy(borrowing);
        doReturn(sumParcels).when(borrowing).sumParcels();

        BigDecimal remainingPaymentAmount = borrowing.remainingPaymentAmount();

        assertEquals(new BigDecimal("20.0"), remainingPaymentAmount);
    }

}
