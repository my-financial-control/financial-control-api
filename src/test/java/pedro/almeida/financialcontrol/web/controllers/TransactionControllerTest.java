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
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.web.config.ConfigConstants;
import pedro.almeida.financialcontrol.web.services.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;
    private final String uri = "/api/v1/transactions";

    @Test
    void registerWithAValidTransactionShouldReturn201AndTheCreatedTransaction() throws Exception {
        Transaction expectedTransaction = TransactionFactory.buildTransaction("Title", "", new BigDecimal("100.0"), "EXPENSE", 1, LocalDate.now(), "Category");
        when(transactionService.register(any())).thenReturn(expectedTransaction);
        String json = """
                {
                    "title": "Água",
                    "description": "Conta de água",
                    "value": 50.95,
                    "type": "EXPENSE",
                    "currentMonth": 10,
                    "date": "2023-10-14",
                    "category": "Category"
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(expectedTransaction.getId().toString()))
                .andExpect(jsonPath("$.title").value(expectedTransaction.getTitle()))
                .andExpect(jsonPath("$.description").value(expectedTransaction.getDescription()))
                .andExpect(jsonPath("$.value").value(expectedTransaction.getValue()))
                .andExpect(jsonPath("$.type").value(expectedTransaction.getType().name()))
                .andExpect(jsonPath("$.currentMonth").value(expectedTransaction.getCurrentMonth().name()))
                .andExpect(jsonPath("$.date").value(expectedTransaction.getDate().toString()))
                .andExpect(jsonPath("$.timestamp").value(expectedTransaction.getTimestamp().format(ConfigConstants.TRANSACTION_TIME_FORMATTER)))
                .andExpect(jsonPath("$.category").value(expectedTransaction.getCategory()));
    }

    @Test
    public void findAllWithMonthAndYearShouldReturn200AndAListOfTransactions() throws Exception {
        List<Transaction> expectedTransactions = Arrays.asList(
                TransactionFactory.buildTransaction("Title 1", "", new BigDecimal("1000.0"), "CREDIT", 1, LocalDate.now(), null),
                TransactionFactory.buildTransaction("Title 2", "", new BigDecimal("200.0"), "EXPENSE", 1, LocalDate.now(), "Category 1"),
                TransactionFactory.buildTransaction("Title 3", "", new BigDecimal("350.52"), "EXPENSE", 1, LocalDate.now(), "Category 2")
        );
        when(transactionService.findAll(1, 2023)).thenReturn(expectedTransactions);

        for (int i = 0; i < expectedTransactions.size(); i++) {
            Transaction transaction = expectedTransactions.get(i);

            mockMvc.perform(MockMvcRequestBuilders.get(uri)
                            .param("month", "1")
                            .param("year", "2023"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.length()").value(expectedTransactions.size()))
                    .andExpect(jsonPath("$[" + i + "].id").value(transaction.getId().toString()))
                    .andExpect(jsonPath("$[" + i + "].title").value(transaction.getTitle()))
                    .andExpect(jsonPath("$[" + i + "].description").value(transaction.getDescription()))
                    .andExpect(jsonPath("$[" + i + "].value").value(transaction.getValue()))
                    .andExpect(jsonPath("$[" + i + "].type").value(transaction.getType().name()))
                    .andExpect(jsonPath("$[" + i + "].currentMonth").value(transaction.getCurrentMonth().name()))
                    .andExpect(jsonPath("$[" + i + "].date").value(transaction.getDate().toString()))
                    .andExpect(jsonPath("$[" + i + "].timestamp").value(transaction.getTimestamp().format(ConfigConstants.TRANSACTION_TIME_FORMATTER)))
                    .andExpect(jsonPath("$[" + i + "].category").value(transaction.getCategory()));
        }
    }


    @Test
    public void findAllWithoutMonthAndYearShouldReturn200AndAListOfTransactions() throws Exception {
        List<Transaction> expectedTransactions = Arrays.asList(
                TransactionFactory.buildTransaction("Title 1", "", new BigDecimal("1000.0"), "CREDIT", 1, LocalDate.now(), null),
                TransactionFactory.buildTransaction("Title 2", "", new BigDecimal("200.0"), "EXPENSE", 1, LocalDate.now(), "Category"),
                TransactionFactory.buildTransaction("Title 3", "", new BigDecimal("780.52"), "CREDIT", 2, LocalDate.now(), null),
                TransactionFactory.buildTransaction("Title 4", "", new BigDecimal("147.71"), "EXPENSE", 2, LocalDate.now(), "Category"),
                TransactionFactory.buildTransaction("Title 5", "", new BigDecimal("108.92"), "CREDIT", 3, LocalDate.now(), null)
        );
        when(transactionService.findAll(null, null)).thenReturn(expectedTransactions);

        for (int i = 0; i < expectedTransactions.size(); i++) {
            Transaction transaction = expectedTransactions.get(i);

            mockMvc.perform(MockMvcRequestBuilders.get(uri))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.length()").value(expectedTransactions.size()))
                    .andExpect(jsonPath("$[" + i + "].id").value(transaction.getId().toString()))
                    .andExpect(jsonPath("$[" + i + "].title").value(transaction.getTitle()))
                    .andExpect(jsonPath("$[" + i + "].description").value(transaction.getDescription()))
                    .andExpect(jsonPath("$[" + i + "].value").value(transaction.getValue()))
                    .andExpect(jsonPath("$[" + i + "].type").value(transaction.getType().name()))
                    .andExpect(jsonPath("$[" + i + "].currentMonth").value(transaction.getCurrentMonth().name()))
                    .andExpect(jsonPath("$[" + i + "].date").value(transaction.getDate().toString()))
                    .andExpect(jsonPath("$[" + i + "].timestamp").value(transaction.getTimestamp().format(ConfigConstants.TRANSACTION_TIME_FORMATTER)))
                    .andExpect(jsonPath("$[" + i + "].category").value(transaction.getCategory()));
        }
    }

}
