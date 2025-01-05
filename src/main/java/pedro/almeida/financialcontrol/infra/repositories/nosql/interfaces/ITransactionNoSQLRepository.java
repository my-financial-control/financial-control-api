package pedro.almeida.financialcontrol.infra.repositories.nosql.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.TransactionEntity;

import java.time.LocalDate;
import java.util.List;

public interface ITransactionNoSQLRepository extends MongoRepository<TransactionEntity, String> {
    List<TransactionEntity> findByType(String type);

    @Query("{ 'currentMonth': ?0, 'date': { '$gte': ?1, '$lt': ?2 } }")
    List<TransactionEntity> findByCurrentMonthAndYear(String currentMonth, LocalDate startDate, LocalDate endDate);

    @Query("{ 'currentMonth': ?0, 'date': { '$gte': ?1, '$lt': ?2 }, 'type': ?3 }")
    List<TransactionEntity> findByCurrentMonthAndYearAndType(String currentMonth, LocalDate startDate, LocalDate endDate, String type);
}
