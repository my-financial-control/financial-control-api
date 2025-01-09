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
import pedro.almeida.financialcontrol.application.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.application.usecases.FindAllBorrowings;
import pedro.almeida.financialcontrol.application.usecases.PayParcelBorrowing;
import pedro.almeida.financialcontrol.application.usecases.RegisterBorrowing;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;

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
class BorrowingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegisterBorrowing registerBorrowing;
    @MockBean
    private FindAllBorrowings findAllBorrowings;
    @MockBean
    private PayParcelBorrowing payParcelBorrowing;
    private final String uri = "/api/v1/borrowings";

    @Test
    void registerWithValidBorrowingShouldReturn201AndTheCreatedBorrowing() throws Exception {
        BorrowingResponseDTO expectedBorrowing = new BorrowingResponseDTO(new Borrowing(new Borrower("Borrower"), new BigDecimal("50.8"), LocalDate.now()));
        when(registerBorrowing.execute(any())).thenReturn(expectedBorrowing);
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
                .andExpect(jsonPath("$.id").value(expectedBorrowing.id().toString()))
                .andExpect(jsonPath("$.borrower").value(expectedBorrowing.borrower()))
                .andExpect(jsonPath("$.value").value(expectedBorrowing.value().toString()))
                .andExpect(jsonPath("$.paid").value(expectedBorrowing.paid()))
                .andExpect(jsonPath("$.date").value(expectedBorrowing.date().toString()))
                .andExpect(jsonPath("$.parcels").isArray());
    }

    @Test
    void findAllShouldReturnAListOfTransactions() throws Exception {
        List<BorrowingResponseDTO> expectedBorrowings = Arrays.asList(
                new BorrowingResponseDTO(new Borrowing(new Borrower("Borrower1"), new BigDecimal("50.8"), LocalDate.now())),
                new BorrowingResponseDTO(new Borrowing(new Borrower("Borrower2"), new BigDecimal("100.8"), LocalDate.now())),
                new BorrowingResponseDTO(new Borrowing(new Borrower("Borrower3"), new BigDecimal("150.8"), LocalDate.now()))
        );
        when(findAllBorrowings.execute()).thenReturn(expectedBorrowings);

        for (int i = 0; i < expectedBorrowings.size(); i++) {
            BorrowingResponseDTO borrowing = expectedBorrowings.get(i);

            mockMvc.perform(MockMvcRequestBuilders.get(uri))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.length()").value(expectedBorrowings.size()))
                    .andExpect(jsonPath("$[" + i + "].id").value(borrowing.id().toString()))
                    .andExpect(jsonPath("$[" + i + "].borrower").value(borrowing.borrower()))
                    .andExpect(jsonPath("$[" + i + "].value").value(borrowing.value().toString()))
                    .andExpect(jsonPath("$[" + i + "].paid").value(borrowing.paid()))
                    .andExpect(jsonPath("$[" + i + "].date").value(borrowing.date().toString()))
                    .andExpect(jsonPath("$[" + i + "].parcels").isArray());
        }
    }

    @Test
    void payParcelWithValidParcelShouldReturn204() throws Exception {
        String json = """
                    {
                        "value": 50.88,
                        "date": "2023-11-18"
                    }
                """;
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri + "/" + uuid + "/parcels")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}