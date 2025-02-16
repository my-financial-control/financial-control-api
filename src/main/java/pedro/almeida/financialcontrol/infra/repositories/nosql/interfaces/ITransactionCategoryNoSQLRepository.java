package pedro.almeida.financialcontrol.infra.repositories.nosql.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.TransactionCategoryEntity;

import java.util.List;

public interface ITransactionCategoryNoSQLRepository extends MongoRepository<TransactionCategoryEntity, String> {
    List<TransactionCategoryEntity> findAllByOrderByNameAsc();
}
