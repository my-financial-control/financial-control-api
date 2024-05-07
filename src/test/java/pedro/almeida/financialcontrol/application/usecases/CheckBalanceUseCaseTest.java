package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.models.Extract;

import java.math.BigDecimal;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckBalanceUseCaseTest {
    @Mock
    private Extract extract;
    private final BigDecimal balanceExpected = new BigDecimal("1528.46");
    private final Month byMonth = Month.JANUARY;
    @InjectMocks
    private CheckBalanceUseCase checkBalanceUseCase;

    @Test
    void executeShouldReturnTheBalanceTest() {
        when(extract.checkBalance()).thenReturn(balanceExpected);

        BigDecimal balance = checkBalanceUseCase.execute();

        assertEquals(balanceExpected, balance);
        verify(extract).checkBalance();
    }

    @Test
    void executeShouldReturnTheBalanceByMonthTest() {
        int byYear = 2023;
        when(extract.checkBalance(byMonth, byYear)).thenReturn(balanceExpected);

        BigDecimal balance = checkBalanceUseCase.execute(byMonth, byYear);

        assertEquals(balanceExpected, balance);
        verify(extract).checkBalance(byMonth, byYear);
    }
}