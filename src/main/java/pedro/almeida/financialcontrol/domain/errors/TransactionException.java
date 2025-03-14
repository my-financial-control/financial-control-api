package pedro.almeida.financialcontrol.domain.errors;

public class TransactionException extends BadRequestException {

    private TransactionException(String message) {
        super(message);
    }

    public static TransactionException invalidTransactionValue() {
        return new TransactionException("O valor da transação deve ser maior que zero");
    }

    public static TransactionException invalidTransactionType() {
        return new TransactionException("A transação deve ser do tipo CREDIT ou EXPENSE");
    }

    public static TransactionException categoryIsRequired() {
        return new TransactionException("Transações do tipo EXPENSE devem informar o parâmetro category");
    }

    public static TransactionException genericError() {
        return new TransactionException("Erro ao processar a transação");
    }
}
