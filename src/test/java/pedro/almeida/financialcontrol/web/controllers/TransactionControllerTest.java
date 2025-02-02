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
import pedro.almeida.financialcontrol.application.dtos.response.CalculateTotalsResponseDTO;
import pedro.almeida.financialcontrol.application.dtos.response.ConsolidatedTransactionResponseDTO;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionCategoryResponseDTO;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionResponseDTO;
import pedro.almeida.financialcontrol.application.usecases.*;
import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.ConsolidatedTransaction;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    private RegisterTransaction registerTransaction;
    @MockBean
    private FindAllTransactions findAllTransactions;
    @MockBean
    private CalculateTransactionsTotals calculateTransactionsTotals;
    @MockBean
    private ConsolidateTransactionsByMonth consolidateTransactionsByMonth;
    @MockBean
    private FindAllTransactionCategories findAllTransactionCategories;
    private final String uri = "/api/v1/transactions";
    private final List<Transaction> transactions = Arrays.asList(
            TransactionFactory.buildTransaction("Title 1", "", new BigDecimal("1000.0"), "CREDIT", 1, LocalDate.now(), null),
            TransactionFactory.buildTransaction("Title 2", "", new BigDecimal("200.0"), "EXPENSE", 1, LocalDate.now(), new TransactionCategory(UUID.randomUUID(), "Category 1", "")),
            TransactionFactory.buildTransaction("Title 3", "", new BigDecimal("780.52"), "CREDIT", 2, LocalDate.now(), null),
            TransactionFactory.buildTransaction("Title 4", "", new BigDecimal("147.71"), "EXPENSE", 2, LocalDate.now(), new TransactionCategory(UUID.randomUUID(), "Category 2", "")),
            TransactionFactory.buildTransaction("Title 5", "", new BigDecimal("108.92"), "CREDIT", 3, LocalDate.now(), null)
    );
    private final List<TransactionResponseDTO> transactionDTOS = TransactionResponseDTO.toTransactionDTO(transactions);
    private final List<ConsolidatedTransactionResponseDTO> consolidatedTransactionDTOS = ConsolidatedTransactionResponseDTO.toConsolidatedTransactionDTO(List.of(new ConsolidatedTransaction(transactions)));

    @Test
    void registerWithAValidTransactionShouldReturn201AndTheCreatedTransaction() throws Exception {
        TransactionResponseDTO expectedTransaction = transactionDTOS.get(0);
        when(registerTransaction.execute(any())).thenReturn(expectedTransaction);
        String json = """
                {
                    "title": "%s",
                    "description": "%s",
                    "value": %s,
                    "type": "%s",
                    "currentMonth": %s,
                    "date": "%s",
                    "categoryId": "%s"
                }""".formatted(
                expectedTransaction.title(),
                expectedTransaction.description(),
                expectedTransaction.value(),
                expectedTransaction.type().name(),
                expectedTransaction.currentMonth().getValue(),
                expectedTransaction.date().toString(),
                expectedTransaction.category().id().toString()
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(expectedTransaction.id().toString()))
                .andExpect(jsonPath("$.title").value(expectedTransaction.title()))
                .andExpect(jsonPath("$.description").value(expectedTransaction.description()))
                .andExpect(jsonPath("$.value").value(expectedTransaction.value()))
                .andExpect(jsonPath("$.type").value(expectedTransaction.type().name()))
                .andExpect(jsonPath("$.currentMonth").value(expectedTransaction.currentMonth().name()))
                .andExpect(jsonPath("$.date").value(expectedTransaction.date().toString()))
                .andExpect(jsonPath("$.timestamp").value(expectedTransaction.timestamp()));
    }

    @Test
    public void findAllTransactionsShouldReturn200AndAListOfTransactions() throws Exception {
        when(findAllTransactions.execute(null, null, null)).thenReturn(transactionDTOS);

        for (int i = 0; i < transactionDTOS.size(); i++) {
            TransactionResponseDTO transaction = transactionDTOS.get(i);

            mockMvc.perform(MockMvcRequestBuilders.get(uri))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.length()").value(transactionDTOS.size()))
                    .andExpect(jsonPath("$[" + i + "].id").value(transaction.id().toString()))
                    .andExpect(jsonPath("$[" + i + "].title").value(transaction.title()))
                    .andExpect(jsonPath("$[" + i + "].description").value(transaction.description()))
                    .andExpect(jsonPath("$[" + i + "].value").value(transaction.value()))
                    .andExpect(jsonPath("$[" + i + "].type").value(transaction.type().name()))
                    .andExpect(jsonPath("$[" + i + "].currentMonth").value(transaction.currentMonth().name()))
                    .andExpect(jsonPath("$[" + i + "].date").value(transaction.date().toString()))
                    .andExpect(jsonPath("$[" + i + "].timestamp").value(transaction.timestamp()));
        }
    }

    @Test
    public void calculateTotalsShouldReturn200AndTheTotalOfCreditsAndExpenses() throws Exception {
        CalculateTotalsResponseDTO expectedTotals = new CalculateTotalsResponseDTO(new BigDecimal("1889.44"), new BigDecimal("348.63"));
        when(calculateTransactionsTotals.execute(1, 2021)).thenReturn(expectedTotals);

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/totals?month=1&year=2021"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.credits").value(expectedTotals.credits()))
                .andExpect(jsonPath("$.expenses").value(expectedTotals.expenses()));
    }

    @Test
    public void consolidatedMonthShouldReturn200AndAListOfConsolidatedTransactions() throws Exception {
        when(consolidateTransactionsByMonth.execute("CREDIT", 1, 2021)).thenReturn(consolidatedTransactionDTOS);

        for (int i = 0; i < consolidatedTransactionDTOS.size(); i++) {
            ConsolidatedTransactionResponseDTO consolidatedTransaction = consolidatedTransactionDTOS.get(i);

            mockMvc.perform(MockMvcRequestBuilders.get(uri + "/consolidated?type=CREDIT&month=1&year=2021"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.length()").value(consolidatedTransactionDTOS.size()))
                    .andExpect(jsonPath("$[" + i + "].title").value(consolidatedTransaction.title()))
                    .andExpect(jsonPath("$[" + i + "].total").value(consolidatedTransaction.total()));
        }
    }

    @Test
    public void findAllCategoriesShouldReturn200AndAListOfTransactionCategories() throws Exception {
        List<TransactionCategory> categories = Arrays.asList(
                new TransactionCategory(UUID.randomUUID(), "Category 1", ""),
                new TransactionCategory(UUID.randomUUID(), "Category 2", ""),
                new TransactionCategory(UUID.randomUUID(), "Category 3", "")
        );
        List<TransactionCategoryResponseDTO> categoryDTOS = categories.stream().map(TransactionCategoryResponseDTO::new).toList();
        when(findAllTransactionCategories.execute()).thenReturn(categoryDTOS);

        for (int i = 0; i < categoryDTOS.size(); i++) {
            TransactionCategoryResponseDTO category = categoryDTOS.get(i);

            mockMvc.perform(MockMvcRequestBuilders.get(uri + "/categories"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.length()").value(categoryDTOS.size()))
                    .andExpect(jsonPath("$[" + i + "].id").value(category.id().toString()))
                    .andExpect(jsonPath("$[" + i + "].name").value(category.name()))
                    .andExpect(jsonPath("$[" + i + "].description").value(category.description()));
        }
    }
}
