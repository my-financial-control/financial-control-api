package pedro.almeida.financialcontrol.domain.errors;

public class ConsolidatedTransactionException extends BadRequestException {
    private ConsolidatedTransactionException(String message) {
        super(message);
    }

    public static ConsolidatedTransactionException noTransactionsToConsolidate() {
        return new ConsolidatedTransactionException("Não há transações para consolidar");
    }
}
