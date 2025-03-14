package pedro.almeida.financialcontrol.domain.errors;

import java.util.UUID;

public class ReceiptException extends BadRequestException {
    private ReceiptException(String message) {
        super(message);
    }

    public static ReceiptException uploadError(String fileName) {
        return new ReceiptException("Erro ao fazer upload do recibo " + fileName);
    }

    public static ReceiptException loadError(UUID id) {
        return new ReceiptException("Erro ao carregar o recibo com id" + id);
    }
}
