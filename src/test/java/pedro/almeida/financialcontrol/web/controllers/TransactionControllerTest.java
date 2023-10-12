package pedro.almeida.financialcontrol.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.web.services.TransactionService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;

    @Test
    public void testFindAllWithMonthAndYear() throws Exception {
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(transactionService.findAll(1, 2023)).thenReturn(expectedTransactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions")
                        .param("month", "1")
                        .param("year", "2023"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindAllWithoutMonthAndYear() throws Exception {
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(transactionService.findAll(null, null)).thenReturn(expectedTransactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
