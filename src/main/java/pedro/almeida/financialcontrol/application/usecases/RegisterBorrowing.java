package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pedro.almeida.financialcontrol.application.dtos.request.BorrowingRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.request.BorrowingWithReceiptDTO;
import pedro.almeida.financialcontrol.application.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.domain.errors.ReceiptException;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.Receipt;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.domain.services.ReceiptService;

import java.io.IOException;

@Component
public class RegisterBorrowing {

    private final Borrowings borrowings;
    private final ReceiptService receiptService;

    public RegisterBorrowing(Borrowings borrowings, ReceiptService receiptService) {
        this.borrowings = borrowings;
        this.receiptService = receiptService;
    }

    public BorrowingResponseDTO execute(BorrowingWithReceiptDTO borrowingWithReceiptDTO) {
        boolean hasReceipt = borrowingWithReceiptDTO.getReceipt() != null;
        Borrowing borrowing = borrowingWithReceiptDTO.getBorrowing().toBorrowing(hasReceipt);
        Borrowing savedBorrowing = borrowings.save(borrowing);

        if (hasReceipt) {
            MultipartFile receipt = borrowingWithReceiptDTO.getReceipt();
            try {
                Receipt r = new Receipt(savedBorrowing.getId(), receipt.getOriginalFilename(), receipt.getContentType(),
                        receipt.getInputStream(), receipt.getSize());
                receiptService.save(r);
            } catch (IOException e) {
                throw ReceiptException.uploadError(receipt.getOriginalFilename());
            }
        }

        return new BorrowingResponseDTO(savedBorrowing);
    }
}
