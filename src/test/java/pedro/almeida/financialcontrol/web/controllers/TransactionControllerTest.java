package pedro.almeida.financialcontrol.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;
import pedro.almeida.financialcontrol.web.services.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;
    private final String uri = "/api/v1/transactions";

    @Test
    void registerShouldReturn201WithAValidTransaction() throws Exception {
        Transaction expectedTransaction = new Transaction("Title", "", new BigDecimal("100.0"), TransactionType.EXPENSE, Month.JANUARY, LocalDate.now());
        when(transactionService.register(any())).thenReturn(expectedTransaction);
        String json = """
                {
                    "title": "Água",
                    "description": "Conta de água",
                    "value": 50.95,
                    "type": "EXPENSE",
                    "currentMonth": 10,
                    "date": "2023-10-14"
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void findAllShouldReturn200WithMonthAndYear() throws Exception {
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(transactionService.findAll(1, 2023)).thenReturn(expectedTransactions);

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .param("month", "1")
                        .param("year", "2023"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findAllShouldReturn200WithoutMonthAndYear() throws Exception {
        List<Transaction> expectedTransactions = new ArrayList<>();
        when(transactionService.findAll(null, null)).thenReturn(expectedTransactions);

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
