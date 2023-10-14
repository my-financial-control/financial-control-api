package pedro.almeida.financialcontrol.web.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalance;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckBalanceServiceTest {

    @Mock
    private CheckBalance checkBalance;
    @InjectMocks
    private CheckBalanceService checkBalanceService;

    @Test
    void checkBalanceShouldReturnTheBalanceForTheEntirePeriod() {
        BigDecimal expected = new BigDecimal("1500.0");
        when(this.checkBalance.execute()).thenReturn(expected);

        BigDecimal balance = this.checkBalanceService.checkBalance();

        assertEquals(expected, balance);
    }

}