package pedro.almeida.financialcontrol.application.dtos.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.multipart.MultipartFile;
import pedro.almeida.financialcontrol.domain.errors.BorrowingException;

public class BorrowingWithReceiptDTO {
    private String borrowing;
    private MultipartFile receipt;

    public BorrowingRequestDTO getBorrowing() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(borrowing, BorrowingRequestDTO.class);
        } catch (JsonProcessingException e) {
            throw BorrowingException.genericError();
        }
    }

    public void setBorrowing(String borrowing) {
        this.borrowing = borrowing;
    }

    public MultipartFile getReceipt() {
        return receipt;
    }

    public void setReceipt(MultipartFile receipt) {
        this.receipt = receipt;
    }
} 