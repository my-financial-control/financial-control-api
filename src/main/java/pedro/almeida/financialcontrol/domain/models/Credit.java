package pedro.almeida.financialcontrol.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class Credit extends Transaction {
    public Credit(UUID id, String title, String description, BigDecimal value, Month currentMonth, LocalDate date, LocalDateTime timestamp) {
        super(id, title, description, value, TransactionType.CREDIT, currentMonth, date, timestamp, new TransactionCategory(UUID.fromString("945d7f72-47b5-4d62-bae0-10d6d13231d7"), "Crédito", "Categoria padrão para crédito"));
    }
}
