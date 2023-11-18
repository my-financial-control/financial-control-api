package pedro.almeida.financialcontrol.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;
import pedro.almeida.financialcontrol.web.dtos.request.BorrowingRequestDTO;
import pedro.almeida.financialcontrol.web.dtos.request.PayParcelBorrrowingRequestDTO;
import pedro.almeida.financialcontrol.web.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.web.services.BorrowingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BorrowingResponseDTO register(@RequestBody BorrowingRequestDTO borrowingRequestDTO) {
        return new BorrowingResponseDTO(borrowingService.register(borrowingRequestDTO.toBorrowing()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BorrowingResponseDTO> findAll() {
        return BorrowingResponseDTO.toBorrowingResponseDTO(borrowingService.findAll());
    }

    @PostMapping("/{id}/parcels")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payParcel(@PathVariable UUID id, @RequestBody PayParcelBorrrowingRequestDTO parcel) {
        borrowingService.payParcel(id, parcel.toParcelBorrowing());
    }

}
