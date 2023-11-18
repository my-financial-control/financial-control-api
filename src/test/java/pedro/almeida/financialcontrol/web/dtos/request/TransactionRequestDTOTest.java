package pedro.almeida.financialcontrol.web.dtos.request;

import org.junit.jupiter.api.Test;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionRequestDTOTest {

    @Test
    void toTransactionShouldReturnATransaction() {
        TransactionRequestDTO dto = new TransactionRequestDTO("Title", "", new BigDecimal("100.0"), "CREDIT", 1, LocalDate.now());

        Transaction transaction = dto.toTransaction();

        assertEquals(dto.title(), transaction.getTitle());
        assertEquals(dto.description(), transaction.getDescription());
        assertEquals(dto.value(), transaction.getValue());
        assertEquals(TransactionType.CREDIT, transaction.getType());
        assertEquals(Month.JANUARY, transaction.getCurrentMonth());
        assertEquals(dto.date(), transaction.getDate());
    }

}