package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.services.ExtractConsultation;

import java.math.BigDecimal;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckBalancePlusRemainingPaymentsTest {
    @Mock
    private ExtractConsultation extractConsultation;
    private final BigDecimal balanceExpected = new BigDecimal("1528.46");
    private final Month byMonth = Month.JANUARY;
    @InjectMocks
    private CheckBalancePlusRemainingPayments checkBalancePlusRemainingPayments;

    @Test
    void executeShouldReturnTheBalanceTest() {
        when(extractConsultation.checkBalancePlusRemainingPayment()).thenReturn(balanceExpected);

        BigDecimal balance = checkBalancePlusRemainingPayments.execute();

        assertEquals(balanceExpected, balance);
        verify(extractConsultation).checkBalancePlusRemainingPayment();
    }

    @Test
    void executeShouldReturnTheBalanceByMonthTest() {
        int byYear = 2023;
        when(extractConsultation.checkBalancePlusRemainingPayment(byMonth, byYear)).thenReturn(balanceExpected);

        BigDecimal balance = checkBalancePlusRemainingPayments.execute(byMonth, byYear);

        assertEquals(balanceExpected, balance);
        verify(extractConsultation).checkBalancePlusRemainingPayment(byMonth, byYear);
    }
}