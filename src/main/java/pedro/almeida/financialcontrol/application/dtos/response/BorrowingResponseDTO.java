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
        Boolean paid,
        LocalDate date,
        List<ParcelBorrowing> parcels,
        LocalDateTime timestamp
) {

    public BorrowingResponseDTO(Borrowing borrowing) {
        this(
                borrowing.getId(),
                borrowing.getBorrower().getName(),
                borrowing.getValue(),
                borrowing.getPaid(),
                borrowing.getDate(),
                borrowing.getParcels(),
                borrowing.getTimestamp()
        );
    }

    public static List<BorrowingResponseDTO> toBorrowingResponseDTO(List<Borrowing> borrowings) {
        return borrowings.stream().map(BorrowingResponseDTO::new).toList();
    }

}
