package pedro.almeida.financialcontrol.infra.repositories.nosql.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.BorrowingEntity;

import java.time.LocalDate;
import java.util.List;

public interface IBorrowingNoSQLRepository extends MongoRepository<BorrowingEntity, String> {
    @Query("{ 'date': { '$gte': ?0, '$lt': ?1 } }")
    List<BorrowingEntity> findByMonthAndYear(LocalDate startDate, LocalDate endDate);
}
