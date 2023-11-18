package pedro.almeida.financialcontrol.web.dtos.response;

import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;
import pedro.almeida.financialcontrol.web.config.ConfigConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionResponseDTOTest {

    @Test
    void toTransactionDTOShouldReturnAListOfTransactionResponseDTO() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("Title 1", "", new BigDecimal("1000.0"), TransactionType.CREDIT, Month.JANUARY, LocalDate.now()),
                new Transaction("Title 2", "", new BigDecimal("200.0"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now()),
                new Transaction("Title 3", "", new BigDecimal("780.52"), TransactionType.CREDIT, Month.FEBRUARY, LocalDate.now()),
                new Transaction("Title 4", "", new BigDecimal("147.71"), TransactionType.EXPENSE, Month.FEBRUARY, LocalDate.now()),
                new Transaction("Title 5", "", new BigDecimal("108.92"), TransactionType.CREDIT, Month.MARCH, LocalDate.now())
        );

        List<TransactionResponseDTO> transactionsDTO = TransactionResponseDTO.toTransactionDTO(transactions);

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            TransactionResponseDTO transactionResponseDTO = transactionsDTO.get(i);
            assertEquals(transaction.getId(), transactionResponseDTO.id());
            assertEquals(transaction.getTitle(), transactionResponseDTO.title());
            assertEquals(transaction.getDescription(), transactionResponseDTO.description());
            assertEquals(transaction.getValue(), transactionResponseDTO.value());
            assertEquals(transaction.getType(), transactionResponseDTO.type());
            assertEquals(transaction.getCurrentMonth(), transactionResponseDTO.currentMonth());
            assertEquals(transaction.getDate(), transactionResponseDTO.date());
            assertEquals(transaction.getTime().format(ConfigConstants.TRANSACTION_TIME_FORMATTER), transactionResponseDTO.time());
        }

    }

}