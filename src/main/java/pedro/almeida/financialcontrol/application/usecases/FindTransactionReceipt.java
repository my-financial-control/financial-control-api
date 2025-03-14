package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import pedro.almeida.financialcontrol.domain.models.Receipt;
import pedro.almeida.financialcontrol.domain.services.ReceiptService;

import java.util.UUID;

@Component
public class FindTransactionReceipt {

    private final ReceiptService receiptService;

    public FindTransactionReceipt(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    public Receipt execute(String transactionId) {
        return receiptService.find(UUID.fromString(transactionId));
    }
}
