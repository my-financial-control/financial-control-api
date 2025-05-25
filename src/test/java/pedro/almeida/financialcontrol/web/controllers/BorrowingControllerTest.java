package pedro.almeida.financialcontrol.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pedro.almeida.financialcontrol.application.dtos.request.BorrowingRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.application.usecases.FindAllBorrowings;
import pedro.almeida.financialcontrol.application.usecases.FindBorrowingReceipt;
import pedro.almeida.financialcontrol.application.usecases.PayParcelBorrowing;
import pedro.almeida.financialcontrol.application.usecases.RegisterBorrowing;
import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;
import pedro.almeida.financialcontrol.domain.models.Receipt;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @MockBean
    private FindBorrowingReceipt findBorrowingReceipt;

    private final String uri = "/api/v1/borrowings";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void registerWithValidBorrowingShouldReturn201AndTheCreatedBorrowing() throws Exception {
        BorrowingRequestDTO borrowingRequestDTO = new BorrowingRequestDTO("Borrower", new BigDecimal("78.73"), "valor",
                LocalDate.of(2025, 5, 25));
        Borrowing borrowing = new Borrowing(new Borrower("Borrower"), new BigDecimal("78.73"), "valor",
                LocalDate.of(2025, 5, 25));
        BorrowingResponseDTO borrowingResponseDTO = new BorrowingResponseDTO(borrowing);

        when(registerBorrowing.execute(any())).thenReturn(borrowingResponseDTO);

        String borrowingJson = objectMapper.writeValueAsString(borrowingRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.multipart(uri)
                .param("borrowing", borrowingJson)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.borrower").value("Borrower"))
                .andExpect(jsonPath("$.description").value("valor"))
                .andExpect(jsonPath("$.value").value(78.73))
                .andExpect(jsonPath("$.date").value("2025-05-25"))
                .andExpect(jsonPath("$.paid").value(false))
                .andExpect(jsonPath("$.hasReceipt").value(false))
                .andExpect(jsonPath("$.parcels").isEmpty())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void registerWithValidBorrowingAndReceiptShouldReturn201AndTheCreatedBorrowing() throws Exception {
        BorrowingRequestDTO borrowingRequestDTO = new BorrowingRequestDTO("Borrower", new BigDecimal("78.73"), "valor",
                LocalDate.of(2025, 5, 25));
        Borrowing borrowing = new Borrowing(new Borrower("Borrower"), new BigDecimal("78.73"), "valor",
                LocalDate.of(2025, 5, 25), true);
        BorrowingResponseDTO borrowingResponseDTO = new BorrowingResponseDTO(borrowing);

        when(registerBorrowing.execute(any())).thenReturn(borrowingResponseDTO);

        String borrowingJson = objectMapper.writeValueAsString(borrowingRequestDTO);

        MockMultipartFile receiptFile = new MockMultipartFile(
                "receipt",
                "receipt.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "receipt content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart(uri)
                .file(receiptFile)
                .param("borrowing", borrowingJson)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.borrower").value("Borrower"))
                .andExpect(jsonPath("$.description").value("valor"))
                .andExpect(jsonPath("$.value").value(78.73))
                .andExpect(jsonPath("$.date").value("2025-05-25"))
                .andExpect(jsonPath("$.paid").value(false))
                .andExpect(jsonPath("$.hasReceipt").value(true))
                .andExpect(jsonPath("$.parcels").isEmpty())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void findAllShouldReturnAListOfBorrowings() throws Exception {
        List<Borrowing> borrowings = Arrays.asList(
                new Borrowing(new Borrower("Borrower"), new BigDecimal("78.73"), "valor", LocalDate.of(2025, 5, 25)),
                new Borrowing(new Borrower("Mãe"), new BigDecimal("78.73"), "valor", LocalDate.of(2025, 5, 25)));
        List<BorrowingResponseDTO> borrowingDTOS = BorrowingResponseDTO.toBorrowingResponseDTO(borrowings);

        when(findAllBorrowings.execute()).thenReturn(borrowingDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].borrower").value("Borrower"))
                .andExpect(jsonPath("$[0].description").value("valor"))
                .andExpect(jsonPath("$[0].value").value(78.73))
                .andExpect(jsonPath("$[0].date").value("2025-05-25"))
                .andExpect(jsonPath("$[1].borrower").value("Mãe"))
                .andExpect(jsonPath("$[1].description").value("valor"))
                .andExpect(jsonPath("$[1].value").value(78.73))
                .andExpect(jsonPath("$[1].date").value("2025-05-25"))
                .andExpect(jsonPath("$[1].paid").value(false))
                .andExpect(jsonPath("$[1].hasReceipt").value(false))
                .andExpect(jsonPath("$[1].parcels").isEmpty())
                .andExpect(jsonPath("$[1].timestamp").exists());
    }

    @Test
    void findReceiptShouldReturn200AndTheBorrowingReceipt() throws Exception {
        String borrowingId = UUID.randomUUID().toString();
        byte[] fileContent = "receipt content".getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
        Receipt receipt = new Receipt(UUID.fromString(borrowingId), "receipt.pdf", MediaType.APPLICATION_PDF_VALUE,
                inputStream, fileContent.length);

        when(findBorrowingReceipt.execute(borrowingId)).thenReturn(receipt);

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/" + borrowingId + "/receipt"))
                .andExpect(status().isOk());
    }

    @Test
    void payParcelWithValidParcelShouldReturn204() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/" + id + "/parcels")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": 78.73, \"date\": \"2025-05-25\"}"))
                .andExpect(status().isNoContent());
    }
}