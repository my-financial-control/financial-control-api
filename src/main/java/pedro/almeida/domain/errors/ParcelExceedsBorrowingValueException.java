package pedro.almeida.domain.errors;

public class ParcelExceedsBorrowingValueException extends RuntimeException{

    public ParcelExceedsBorrowingValueException(String message) {
        super(message);
    }

}
