package pedro.almeida.financialcontrol.infra.repositories.nosql.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.TransactionCategoryEntity;

public interface ITransactionCategoryNoSQLRepository extends MongoRepository<TransactionCategoryEntity, String> {
}
