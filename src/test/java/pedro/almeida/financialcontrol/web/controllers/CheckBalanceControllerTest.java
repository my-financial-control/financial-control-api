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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CheckBalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CheckBalanceService checkBalanceService;
    private final String uri = "/api/v1/check-balance";

    @Test
    void checkBalanceShouldReturn200WithTheValueOfTheBalance() throws Exception {
        BigDecimal expectedBalance = new BigDecimal("1500.0");
        when(checkBalanceService.checkBalance()).thenReturn(expectedBalance);

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.balance").value(expectedBalance));
    }

}