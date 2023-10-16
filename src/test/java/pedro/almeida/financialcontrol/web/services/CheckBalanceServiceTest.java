package pedro.almeida.financialcontrol.web.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.domain.usecases.CheckBalance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckBalanceServiceTest {

    @Mock
    private CheckBalance checkBalance;
    @InjectMocks
    private CheckBalanceService checkBalanceService;

    @Test
    void checkBalanceShouldReturnTheBalanceForTheEntirePeriodWhenMonthAndYearAreNull() {
        BigDecimal expected = new BigDecimal("1500.0");
        when(checkBalance.execute()).thenReturn(expected);

        BigDecimal balance = checkBalanceService.checkBalance(null, null);

        verify(checkBalance).execute();
        assertEquals(expected, balance);
    }

    @Test
    void checkBalanceShouldReturnTheBalanceOfTheMonthAndYearInformed() {
        BigDecimal expected = new BigDecimal("1500.0");
        int month = 1;
        int year = 2023;
        when(checkBalance.execute(Month.of(month), year)).thenReturn(expected);

        BigDecimal balance = checkBalanceService.checkBalance(month, year);

        verify(checkBalance).execute(Month.of(month), year);
        assertEquals(expected, balance);
    }

    @Test
    void checkBalanceShouldReturnTheBalanceOfTheMonthOfTheCurrentYearWhenOnlyTheMonthWasInformed() {
        BigDecimal expected = new BigDecimal("1500.0");
        int month = 1;
        when(checkBalance.execute(any(), anyInt())).thenReturn(expected);

        BigDecimal balance = checkBalanceService.checkBalance(month, null);

        verify(checkBalance).execute(Month.of(month), LocalDate.now().getYear());
        assertEquals(expected, balance);
    }

}