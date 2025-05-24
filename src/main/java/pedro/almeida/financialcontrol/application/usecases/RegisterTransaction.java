package pedro.almeida.financialcontrol.application.usecases;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pedro.almeida.financialcontrol.application.dtos.request.TransactionRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.request.TransactionWithReceiptDTO;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionResponseDTO;
import pedro.almeida.financialcontrol.domain.errors.ReceiptException;
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Receipt;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;
import pedro.almeida.financialcontrol.domain.repositories.TransactionCategories;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.domain.services.ReceiptService;

import java.io.IOException;
import java.util.UUID;

@Component
public class RegisterTransaction {

    private final Transactions transactions;
    private final TransactionCategories transactionCategories;
    private final ReceiptService receiptService;

    public RegisterTransaction(Transactions transactions, TransactionCategories transactionCategories, ReceiptService receiptService) {
        this.transactions = transactions;
        this.transactionCategories = transactionCategories;
        this.receiptService = receiptService;
    }

    public TransactionResponseDTO execute(TransactionWithReceiptDTO transactionWithReceiptDTO) {
        TransactionRequestDTO transactionDTO = transactionWithReceiptDTO.getTransaction();
        TransactionCategory category = transactionCategories.findById(UUID.fromString(transactionDTO.categoryId()));

        boolean hasReceipt = transactionWithReceiptDTO.getReceipt() != null;
        Transaction transaction = TransactionFactory.buildTransaction(transactionDTO.title(), transactionDTO.description(), transactionDTO.value(), transactionDTO.type(), transactionDTO.currentMonth(), transactionDTO.currentYear(), transactionDTO.date(), category, hasReceipt);
        Transaction savedTransaction = transactions.save(transaction);

        if (hasReceipt) {
            MultipartFile receipt = transactionWithReceiptDTO.getReceipt();
            try {
                Receipt r = new Receipt(savedTransaction.getId(), receipt.getOriginalFilename(), receipt.getContentType(), receipt.getInputStream(), receipt.getSize());
                receiptService.save(r);
            } catch (IOException e) {
                throw ReceiptException.uploadError(receipt.getOriginalFilename());
            }
        }

        return new TransactionResponseDTO(savedTransaction);
    }

}
