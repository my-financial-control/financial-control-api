package pedro.almeida.financialcontrol.domain.errors;

public class BorrowingException extends BadRequestException {

    private BorrowingException(String message) {
        super(message);
    }

    public static BorrowingException parcelExceedsBorrowingValue() {
        return new BorrowingException("O valor da parcela excede o valor restante a pagar do empréstimo");
    }

    public static BorrowingException invalidBorrowingValue() {
        return new BorrowingException("O valor do empréstimo deve ser maior que zero");
    }

}
