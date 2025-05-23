package pedro.almeida.financialcontrol.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class Credit extends Transaction {
    public Credit(UUID id, String title, String description, BigDecimal value, Month currentMonth, Integer currentYear, LocalDate date, LocalDateTime timestamp, TransactionCategory category, Boolean hasReceipt) {
        super(id, title, description, value, TransactionType.CREDIT, currentMonth, currentYear, date, timestamp, category, hasReceipt);
    }
}
