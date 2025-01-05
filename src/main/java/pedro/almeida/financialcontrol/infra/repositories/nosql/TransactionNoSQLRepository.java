package pedro.almeida.financialcontrol.infra.repositories.nosql;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pedro.almeida.financialcontrol.domain.models.Transaction;
import pedro.almeida.financialcontrol.domain.models.TransactionType;
import pedro.almeida.financialcontrol.domain.repositories.Transactions;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.TransactionEntity;
import pedro.almeida.financialcontrol.infra.repositories.nosql.interfaces.ITransactionNoSQLRepository;
import pedro.almeida.financialcontrol.infra.repositories.nosql.mappers.TransactionMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static pedro.almeida.financialcontrol.infra.repositories.nosql.mappers.TransactionMapper.fromEntity;
import static pedro.almeida.financialcontrol.infra.repositories.nosql.mappers.TransactionMapper.toEntity;

@Repository
public class TransactionNoSQLRepository implements Transactions {

    private final ITransactionNoSQLRepository repository;
    private final MongoTemplate mongoTemplate;
    private final String transactionsCollection = "transactions";

    public TransactionNoSQLRepository(ITransactionNoSQLRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    @Override
    public Transaction save(Transaction transaction) {
        return fromEntity(repository.save(toEntity(transaction)));
    }

    @Override
    public List<Transaction> findAll() {
        List<TransactionEntity> transactions = repository.findAll();
        return transactions.stream().map(TransactionMapper::fromEntity).toList();
    }

    @Override
    public List<Transaction> findAll(TransactionType type) {
        List<TransactionEntity> transactions = repository.findByType(type.name());
        return transactions.stream().map(TransactionMapper::fromEntity).toList();
    }

    @Override
    public List<Transaction> findAll(Month month, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        List<TransactionEntity> transactions = repository.findByCurrentMonthAndYear(month.name(), startDate, endDate);
        return transactions.stream().map(TransactionMapper::fromEntity).toList();
    }

    @Override
    public List<Transaction> findAll(Month month, int year, TransactionType type) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        List<TransactionEntity> transactions = repository.findByCurrentMonthAndYearAndType(month.name(), startDate, endDate, type.name());
        return transactions.stream().map(TransactionMapper::fromEntity).toList();
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
