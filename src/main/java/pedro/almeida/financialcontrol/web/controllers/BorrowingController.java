package pedro.almeida.financialcontrol.web.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.application.dtos.request.BorrowingRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.request.PayParcelBorrowingRequestDTO;
import pedro.almeida.financialcontrol.application.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.application.usecases.FindAllBorrowings;
import pedro.almeida.financialcontrol.application.usecases.PayParcelBorrowing;
import pedro.almeida.financialcontrol.application.usecases.RegisterBorrowing;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowings")
public class BorrowingController {
    private final RegisterBorrowing registerBorrowing;
    private final FindAllBorrowings findAllBorrowings;
    private final PayParcelBorrowing payParcelBorrowing;

    public BorrowingController(RegisterBorrowing registerBorrowing, FindAllBorrowings findAllBorrowings, PayParcelBorrowing payParcelBorrowing) {
        this.registerBorrowing = registerBorrowing;
        this.findAllBorrowings = findAllBorrowings;
        this.payParcelBorrowing = payParcelBorrowing;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BorrowingResponseDTO register(@RequestBody @Valid BorrowingRequestDTO request) {
        return registerBorrowing.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BorrowingResponseDTO> findAll() {
        return findAllBorrowings.execute();
    }

    @PostMapping("/{id}/parcels")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payParcel(@PathVariable UUID id, @RequestBody @Valid PayParcelBorrowingRequestDTO parcel) {
        payParcelBorrowing.execute(id, parcel);
    }
}
