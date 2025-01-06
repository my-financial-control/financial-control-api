package pedro.almeida.financialcontrol.infra.repositories.nosql.mappers;

import pedro.almeida.financialcontrol.domain.factories.TransactionFactory;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.TransactionEntity;

import java.time.Month;
import java.util.UUID;

public class TransactionMapper {
    public static TransactionEntity toEntity(Transaction transaction) {
        return new TransactionEntity(
                transaction.getId().toString(),
                transaction.getTitle(),
                transaction.getDescription(),
                transaction.getValue(),
                transaction.getType().name(),
                transaction.getCurrentMonth().name(),
                transaction.getDate(),
                transaction.getTimestamp(),
                transaction.getCategory()
        );
    }

    public static Transaction fromEntity(TransactionEntity transaction) {
        return TransactionFactory.buildTransaction(
                UUID.fromString(transaction.getId()),
                transaction.getTitle(),
                transaction.getDescription(),
                transaction.getValue(),
                transaction.getType(),
                Month.valueOf(transaction.getCurrentMonth()).getValue(),
                transaction.getDate(),
                transaction.getTimestamp(),
                transaction.getCategory()
        );
    }
}
