package pedro.almeida.financialcontrol.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pedro.almeida.financialcontrol.web.services.CheckBalanceService;

import java.math.BigDecimal;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CheckBalanceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CheckBalanceService checkBalanceService;
    private final String baseUri = "/api/v1/check-balance";

    @Test
    void checkBalanceWithoutMonthAndYearShouldReturn200WithTheValueOfTheBalanceOfEntirePeriod() throws Exception {
        BigDecimal expectedBalance = new BigDecimal("1500.0");
        when(checkBalanceService.checkBalance(any(), any())).thenReturn(expectedBalance);

        mockMvc.perform(MockMvcRequestBuilders.get(baseUri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.balance").value(expectedBalance));
    }

    @Test
    void checkBalanceWithMonthAndYearShouldReturn200WithTheValueOfTheBalanceOfSpecifiedPeriod() throws Exception {
        BigDecimal expectedBalance = new BigDecimal("800.5");
        when(checkBalanceService.checkBalance(1, 2023)).thenReturn(expectedBalance);

        mockMvc.perform(MockMvcRequestBuilders.get(baseUri)
                        .param("month", "1")
                        .param("year", "2023"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.balance").value(expectedBalance));
    }

    @Test
    void checkBalancePlusRemainingPaymentsWithoutMonthAndYearShouldReturn200WithTheValueOfTheBalanceOfEntirePeriod() throws Exception {
        BigDecimal expectedBalance = new BigDecimal("1500.0");
        when(checkBalanceService.checkBalancePlusRemainingPayments(any(), any())).thenReturn(expectedBalance);

        mockMvc.perform(MockMvcRequestBuilders.get(baseUri + "/plus-remaining-payments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.balance").value(expectedBalance));
    }

    @Test
    void checkBalancePlusRemainingPaymentsWithMonthAndYearShouldReturn200WithTheValueOfTheBalanceOfSpecifiedPeriod() throws Exception {
        BigDecimal expectedBalance = new BigDecimal("800.5");
        when(checkBalanceService.checkBalancePlusRemainingPayments(1, 2023)).thenReturn(expectedBalance);

        mockMvc.perform(MockMvcRequestBuilders.get(baseUri + "/plus-remaining-payments")
                        .param("month", "1")
                        .param("year", "2023"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.balance").value(expectedBalance));
    }
}