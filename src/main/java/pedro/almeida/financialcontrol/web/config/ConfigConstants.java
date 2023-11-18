package pedro.almeida.financialcontrol.web.config;

import java.time.format.DateTimeFormatter;

public interface ConfigConstants {
    DateTimeFormatter TRANSACTION_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");
}
