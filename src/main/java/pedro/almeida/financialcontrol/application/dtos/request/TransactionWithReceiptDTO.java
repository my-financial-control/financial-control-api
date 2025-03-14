package pedro.almeida.financialcontrol.application.dtos.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.multipart.MultipartFile;
import pedro.almeida.financialcontrol.domain.errors.TransactionException;

public class TransactionWithReceiptDTO {
    private String transaction;
    private MultipartFile receipt;

    public TransactionRequestDTO getTransaction() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(transaction, TransactionRequestDTO.class);
        } catch (JsonProcessingException e) {
            throw TransactionException.genericError();
        }
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public MultipartFile getReceipt() {
        return receipt;
    }

    public void setReceipt(MultipartFile receipt) {
        this.receipt = receipt;
    }
}
