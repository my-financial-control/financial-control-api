package pedro.almeida.financialcontrol.domain.errors;

public class BadRequestException extends RuntimeException {

    protected BadRequestException(String message) {
        super(message);
    }

}
