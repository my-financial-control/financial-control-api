package pedro.almeida.financialcontrol.application.dtos.response;

import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BorrowingResponseDTO(
        UUID id,
        String borrower,
        BigDecimal value,
        String description,
        Boolean paid,
        LocalDate date,
        List<ParcelBorrowing> parcels,
        LocalDateTime timestamp,
        boolean hasReceipt
) {

    public BorrowingResponseDTO(Borrowing borrowing) {
        this(
                borrowing.getId(),
                borrowing.getBorrower().getName(),
                borrowing.getValue(),
                borrowing.getDescription(),
                borrowing.getPaid(),
                borrowing.getDate(),
                borrowing.getParcels(),
                borrowing.getTimestamp(),
                borrowing.hasReceipt()
        );
    }

    public static List<BorrowingResponseDTO> toBorrowingResponseDTO(List<Borrowing> borrowings) {
        return borrowings.stream().map(BorrowingResponseDTO::new).toList();
    }

}
