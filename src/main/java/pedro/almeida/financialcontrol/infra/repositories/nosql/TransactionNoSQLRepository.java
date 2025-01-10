package pedro.almeida.financialcontrol.infra.repositories.nosql;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pedro.almeida.financialcontrol.domain.errors.NotFoundException;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.TransactionCategoryEntity;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.TransactionEntity;
import pedro.almeida.financialcontrol.infra.repositories.nosql.interfaces.ITransactionCategoryNoSQLRepository;
import pedro.almeida.financialcontrol.infra.repositories.nosql.interfaces.ITransactionNoSQLRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Repository
public class TransactionNoSQLRepository implements Transactions {
    private final ITransactionNoSQLRepository transactionsRepository;
    private final ITransactionCategoryNoSQLRepository categoriesRepository;
    private final MongoTemplate mongoTemplate;
    private final String transactionsCollection = "transactions";

    public TransactionNoSQLRepository(ITransactionNoSQLRepository repository, ITransactionCategoryNoSQLRepository categoriesRepository, MongoTemplate mongoTemplate) {
        this.transactionsRepository = repository;
        this.categoriesRepository = categoriesRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity(transaction);
        TransactionCategoryEntity category = categoriesRepository.findById(entity.getCategoryId()).orElseThrow(() -> new NotFoundException("Categoria com id " + entity.getCategoryId() + " não encontrada"));
        return transactionsRepository.save(entity).toModel(category.toModel());
    }

    @Override
    public List<Transaction> findAll() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup(
                        "categories",
                        "categoryId",
                        "_id",
                        "category"
                ),
                Aggregation.unwind("category", true)
        );

        AggregationResults<TransactionEntity> results = mongoTemplate.aggregate(agg, transactionsCollection, TransactionEntity.class);
        List<TransactionEntity> entities = results.getMappedResults();

        return entities.stream()
                .map(entity -> entity.toModel(entity.getCategory() != null ? entity.getCategory().toModel() : null))
                .toList();
    }

    @Override
    public List<Transaction> findAll(TransactionType type) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is(type.name())),
                Aggregation.lookup(
                        "categories",
                        "categoryId",
                        "_id",
                        "category"
                ),
                Aggregation.unwind("category", true)
        );

        AggregationResults<TransactionEntity> results = mongoTemplate.aggregate(agg, transactionsCollection, TransactionEntity.class);
        List<TransactionEntity> entities = results.getMappedResults();

        return entities.stream()
                .map(entity -> entity.toModel(entity.getCategory() != null ? entity.getCategory().toModel() : null))
                .toList();
    }

    @Override
    public List<Transaction> findAll(Month month, int year) {
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date").gte(startOfYear).lte(endOfYear)),
                Aggregation.match(Criteria.where("currentMonth").is(month.name())),
                Aggregation.lookup(
                        "categories",
                        "categoryId",
                        "_id",
                        "category"
                ),
                Aggregation.unwind("category", true)
        );

        AggregationResults<TransactionEntity> results = mongoTemplate.aggregate(agg, transactionsCollection, TransactionEntity.class);
        List<TransactionEntity> entities = results.getMappedResults();

        return entities.stream()
                .map(entity -> entity.toModel(entity.getCategory() != null ? entity.getCategory().toModel() : null))
                .toList();
    }

    @Override
    public List<Transaction> findAll(Month month, int year, TransactionType type) {
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date").gte(startOfYear).lte(endOfYear)),
                Aggregation.match(Criteria.where("currentMonth").is(month.name())),
                Aggregation.match(Criteria.where("type").is(type.name())),
                Aggregation.lookup(
                        "categories",
                        "categoryId",
                        "_id",
                        "category"
                ),
                Aggregation.unwind("category", true)
        );

        AggregationResults<TransactionEntity> results = mongoTemplate.aggregate(agg, transactionsCollection, TransactionEntity.class);
        List<TransactionEntity> entities = results.getMappedResults();

        return entities.stream()
                .map(entity -> entity.toModel(entity.getCategory() != null ? entity.getCategory().toModel() : null))
                .toList();
    }

    @Override
    public BigDecimal sumOfCredits() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is(TransactionType.CREDIT.name())),
                Aggregation.group().sum("value").as("total")
        );
        AggregationResults<Document> result = mongoTemplate.aggregate(agg, transactionsCollection, Document.class);
        Document total = result.getUniqueMappedResult();

        return total != null ? new BigDecimal(total.get("total").toString()) : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumOfCredits(Month month, int year) {
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date").gte(startOfYear).lte(endOfYear)),
                Aggregation.match(Criteria.where("type").is(TransactionType.CREDIT.name())),
                Aggregation.match(Criteria.where("currentMonth").is(month.name())),
                Aggregation.group().sum("value").as("total")
        );
        AggregationResults<Document> result = mongoTemplate.aggregate(agg, transactionsCollection, Document.class);
        Document total = result.getUniqueMappedResult();

        return total != null ? new BigDecimal(total.get("total").toString()) : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumOfExpenses() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is("EXPENSE")),
                Aggregation.group().sum("value").as("totalValue")
        );
        AggregationResults<Document> result = mongoTemplate.aggregate(agg, transactionsCollection, Document.class);
        Document total = result.getUniqueMappedResult();

        return total != null ? new BigDecimal(total.get("totalValue").toString()) : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumOfExpenses(Month month, int year) {
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date").gte(startOfYear).lte(endOfYear)),
                Aggregation.match(Criteria.where("type").is(TransactionType.EXPENSE.name())),
                Aggregation.match(Criteria.where("currentMonth").is(month.name())),
                Aggregation.group().sum("value").as("total")
        );
        AggregationResults<Document> result = mongoTemplate.aggregate(agg, transactionsCollection, Document.class);
        Document total = result.getUniqueMappedResult();

        return total != null ? new BigDecimal(total.get("total").toString()) : BigDecimal.ZERO;
    }
}
