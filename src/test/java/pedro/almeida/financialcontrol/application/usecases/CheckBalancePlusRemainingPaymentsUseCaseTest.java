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
class CheckBalancePlusRemainingPaymentsUseCaseTest {
    @Mock
    private Extract extract;
    private final BigDecimal balanceExpected = new BigDecimal("1528.46");
    private final Month byMonth = Month.JANUARY;
    @InjectMocks
    private CheckBalancePlusRemainingPaymentsUseCase checkBalancePlusRemainingPaymentsUseCase;

    @Test
    void executeShouldReturnTheBalanceTest() {
        when(extract.checkBalancePlusRemainingPayment()).thenReturn(balanceExpected);

        BigDecimal balance = checkBalancePlusRemainingPaymentsUseCase.execute();

        assertEquals(balanceExpected, balance);
        verify(extract).checkBalancePlusRemainingPayment();
    }

    @Test
    void executeShouldReturnTheBalanceByMonthTest() {
        int byYear = 2023;
        when(extract.checkBalancePlusRemainingPayment(byMonth, byYear)).thenReturn(balanceExpected);

        BigDecimal balance = checkBalancePlusRemainingPaymentsUseCase.execute(byMonth, byYear);

        assertEquals(balanceExpected, balance);
        verify(extract).checkBalancePlusRemainingPayment(byMonth, byYear);
    }
}