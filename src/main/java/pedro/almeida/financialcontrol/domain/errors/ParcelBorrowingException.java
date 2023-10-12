package pedro.almeida.financialcontrol.domain.errors;

public class ParcelBorrowingException extends RuntimeException{

    private ParcelBorrowingException(String message) {
        super(message);
    }

    public static ParcelBorrowingException invalidParcelBorrowingValue() {
        return new ParcelBorrowingException("O valor da parcela do empréstimo deve ser maior que zero");
    }

}
