package pedro.almeida.financialcontrol.infra.repositories.nosql.mappers;

import pedro.almeida.financialcontrol.domain.models.Borrower;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.BorrowerEntity;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.BorrowingEntity;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.ParcelBorrowingEntity;

import java.util.UUID;

public class BorrowingMapper {
    public static BorrowingEntity toEntity(Borrowing borrowing) {
        return new BorrowingEntity(
                borrowing.getId().toString(),
                new BorrowerEntity(borrowing.getBorrower().getName()),
                borrowing.getValue(),
                borrowing.getPaid(),
                borrowing.getDate(),
                borrowing.getParcels().stream().map(p -> new ParcelBorrowingEntity(p.getValue(), p.getDate())).toList(),
                borrowing.getTimestamp()
        );
    }

    public static Borrowing fromEntity(BorrowingEntity borrowing) {
        return new Borrowing(
                UUID.fromString(borrowing.getId()),
                new Borrower(borrowing.getBorrower().getName()),
                borrowing.getValue(),
                borrowing.getPaid(),
                borrowing.getDate(),
                borrowing.getParcels().stream().map(p -> new ParcelBorrowing(p.getValue(), p.getDate())).toList(),
                borrowing.getTimestamp()
        );
    }
}
