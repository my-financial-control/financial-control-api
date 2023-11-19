package pedro.almeida.financialcontrol.domain.errors;

public class TransactionException extends BadRequestException {

    private TransactionException(String message) {
        super(message);
    }

    public static TransactionException invalidTransactionValue() {
        return new TransactionException("O valor da transação deve ser maior que zero");
    }

}
