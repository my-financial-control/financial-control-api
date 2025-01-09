package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.application.dtos.response.CheckBalanceResponseDTO;
import pedro.almeida.financialcontrol.domain.services.*;

import java.math.BigDecimal;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckBalanceTest {
    @Mock
    private ExtractConsultation extractConsultation;
    private final CheckBalanceResponseDTO balanceExpected = new CheckBalanceResponseDTO(BigDecimal.valueOf(1000));
    private final Month byMonth = Month.JANUARY;
    @InjectMocks
    private CheckBalance checkBalance;

    @Test
    void executeShouldReturnTheBalanceTest() {
        when(extractConsultation.checkBalance()).thenReturn(balanceExpected.balance());

        CheckBalanceResponseDTO balance = checkBalance.execute();

        assertEquals(balanceExpected, balance);
        verify(extractConsultation).checkBalance();
    }

    @Test
    void executeShouldReturnTheBalanceByMonthTest() {
        int byYear = 2023;
        when(extractConsultation.checkBalance(byMonth, byYear)).thenReturn(balanceExpected.balance());

        CheckBalanceResponseDTO balance = checkBalance.execute(byMonth.getValue(), byYear);

        assertEquals(balanceExpected, balance);
        verify(extractConsultation).checkBalance(byMonth, byYear);
    }
}