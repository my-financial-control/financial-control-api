package pedro.almeida.domain.errors;

public class TransactionException extends RuntimeException {

    private TransactionException(String message) {
        super(message);
    }

    public static TransactionException invalidTransactionValue() {
        return new TransactionException("O valor da transação deve ser maior que zero");
    }

}
