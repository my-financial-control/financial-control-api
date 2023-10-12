package pedro.almeida.domain.errors;

public class BorrowingException extends RuntimeException{

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
