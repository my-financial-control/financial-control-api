package pedro.almeida.financialcontrol.domain.services;

import pedro.almeida.financialcontrol.domain.models.Receipt;
import pedro.almeida.financialcontrol.domain.services.interfaces.Storage;

import java.util.UUID;

public class ReceiptService {
    private final Storage storage;

    public ReceiptService(Storage storage) {
        this.storage = storage;
    }

    public void save(Receipt receipt) {
        storage.save(receipt);
    }

    public Receipt find(UUID transactionId) {
        return storage.find(transactionId);
    }
}
