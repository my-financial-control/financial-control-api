package pedro.almeida.financialcontrol.web.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.application.dtos.request.BorrowingWithReceiptDTO;
import pedro.almeida.financialcontrol.application.dtos.request.PayParcelBorrowingRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.application.usecases.FindAllBorrowings;
import pedro.almeida.financialcontrol.application.usecases.FindBorrowingReceipt;
import pedro.almeida.financialcontrol.application.usecases.PayParcelBorrowing;
import pedro.almeida.financialcontrol.application.usecases.RegisterBorrowing;
import pedro.almeida.financialcontrol.domain.models.Receipt;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowings")
public class BorrowingController {
    private final RegisterBorrowing registerBorrowing;
    private final FindAllBorrowings findAllBorrowings;
    private final PayParcelBorrowing payParcelBorrowing;
    private final FindBorrowingReceipt findBorrowingReceipt;

    public BorrowingController(RegisterBorrowing registerBorrowing, FindAllBorrowings findAllBorrowings,
            PayParcelBorrowing payParcelBorrowing, FindBorrowingReceipt findBorrowingReceipt) {
        this.registerBorrowing = registerBorrowing;
        this.findAllBorrowings = findAllBorrowings;
        this.payParcelBorrowing = payParcelBorrowing;
        this.findBorrowingReceipt = findBorrowingReceipt;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BorrowingResponseDTO register(@ModelAttribute BorrowingWithReceiptDTO request) {
        return registerBorrowing.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BorrowingResponseDTO> findAll() {
        return findAllBorrowings.execute();
    }

    @GetMapping("/{id}/receipt")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> findReceipt(@PathVariable("id") String borrowingId) throws IOException {
        Receipt receipt = findBorrowingReceipt.execute(borrowingId);

        if (receipt == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileContent = receipt.getContent().readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + receipt.getFileName());
        headers.add(HttpHeaders.CONTENT_TYPE, receipt.getContentType());

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/parcels")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payParcel(@PathVariable UUID id, @RequestBody @Valid PayParcelBorrowingRequestDTO parcel) {
        payParcelBorrowing.execute(id, parcel);
    }
}
