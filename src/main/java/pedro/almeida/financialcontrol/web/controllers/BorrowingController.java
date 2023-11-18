package pedro.almeida.financialcontrol.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pedro.almeida.financialcontrol.web.dtos.request.BorrowingRequestDTO;
import pedro.almeida.financialcontrol.web.dtos.response.BorrowingResponseDTO;
import pedro.almeida.financialcontrol.web.services.BorrowingService;

import java.util.List;

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

}
