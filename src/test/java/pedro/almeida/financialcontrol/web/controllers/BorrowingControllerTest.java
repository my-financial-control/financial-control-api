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
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.web.services.BorrowingService;

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
class BorrowingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BorrowingService borrowingService;
    private final String uri = "/api/v1/borrowings";

    @Test
    void registerWithValidBorrowingShouldReturn201AndTheCreatedBorrowing() throws Exception {
        Borrowing expectedBorrowing = new Borrowing(new Borrower("Borrower"), new BigDecimal("50.8"), LocalDate.now());
        when(borrowingService.register(any())).thenReturn(expectedBorrowing);
        String json = """
                    {
                        "borrower": "Borrower",
                        "value": 50.80,
                        "date": "2023-11-18"
                    }
                """;

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(expectedBorrowing.getId().toString()))
                .andExpect(jsonPath("$.borrower").value(expectedBorrowing.getBorrower().getName()))
                .andExpect(jsonPath("$.value").value(expectedBorrowing.getValue().toString()))
                .andExpect(jsonPath("$.paid").value(expectedBorrowing.getPaid()))
                .andExpect(jsonPath("$.date").value(expectedBorrowing.getDate().toString()))
                .andExpect(jsonPath("$.parcels").isArray());
    }

    @Test
    void findAllShouldReturnAListOfTransactions() throws Exception {
        List<Borrowing> expectedBorrowings = Arrays.asList(
                new Borrowing(new Borrower("Borrower 1"), new BigDecimal("50.8")),
                new Borrowing(new Borrower("Borrower 2"), new BigDecimal("100.7")),
                new Borrowing(new Borrower("Borrower 3"), new BigDecimal("200")),
                new Borrowing(new Borrower("Borrower 4"), new BigDecimal("20.89")),
                new Borrowing(new Borrower("Borrower 5"), new BigDecimal("33.74"))
        );
        when(borrowingService.findAll()).thenReturn(expectedBorrowings);

        for (int i = 0; i < expectedBorrowings.size(); i++) {
            Borrowing borrowing = expectedBorrowings.get(i);

            mockMvc.perform(MockMvcRequestBuilders.get(uri))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.length()").value(expectedBorrowings.size()))
                    .andExpect(jsonPath("$[" + i + "].id").value(borrowing.getId().toString()))
                    .andExpect(jsonPath("$[" + i + "].borrower").value(borrowing.getBorrower().getName()))
                    .andExpect(jsonPath("$[" + i + "].value").value(borrowing.getValue().toString()))
                    .andExpect(jsonPath("$[" + i + "].paid").value(borrowing.getPaid()))
                    .andExpect(jsonPath("$[" + i + "].date").value(borrowing.getDate().toString()))
                    .andExpect(jsonPath("$[" + i + "].parcels").isArray());
        }
    }

}