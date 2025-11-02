package pedro.almeida.financialcontrol.infra.repositories.nosql;

import org.springframework.data.mongodb.core.MongoTemplate;
import pedro.almeida.financialcontrol.domain.repositories.Lookups;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.TransactionEntity;

import java.util.List;

public class LookupNoSQLRepository implements Lookups {
    private final MongoTemplate mongoTemplate;

    public LookupNoSQLRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<String> transactionTitles() {
        return mongoTemplate.query(TransactionEntity.class)
                .distinct("title")
                .as(String.class)
                .all();
    }
}
