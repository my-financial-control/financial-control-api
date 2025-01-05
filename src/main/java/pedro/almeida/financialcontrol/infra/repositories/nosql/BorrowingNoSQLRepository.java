package pedro.almeida.financialcontrol.infra.repositories.nosql;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pedro.almeida.financialcontrol.domain.models.Borrowing;
import pedro.almeida.financialcontrol.domain.repositories.Borrowings;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.BorrowingEntity;
import pedro.almeida.financialcontrol.infra.repositories.nosql.interfaces.IBorrowingNoSQLRepository;
import pedro.almeida.financialcontrol.infra.repositories.nosql.mappers.BorrowingMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pedro.almeida.financialcontrol.infra.repositories.nosql.mappers.BorrowingMapper.fromEntity;
import static pedro.almeida.financialcontrol.infra.repositories.nosql.mappers.BorrowingMapper.toEntity;

@Repository
public class BorrowingNoSQLRepository implements Borrowings {
    private final IBorrowingNoSQLRepository repository;
    private final MongoTemplate mongoTemplate;
    private final String borrowingsCollection = "borrowings";

    public BorrowingNoSQLRepository(IBorrowingNoSQLRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    @Override
    public Borrowing save(Borrowing borrowing) {
        return fromEntity(repository.save(toEntity(borrowing)));
    }

    @Override
    public List<Borrowing> findAll() {
        List<BorrowingEntity> borrowings = repository.findAll();
        return borrowings.stream().map(BorrowingMapper::fromEntity).toList();
    }

    @Override
    public List<Borrowing> findAll(Month month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, 1).plusMonths(1);

        List<BorrowingEntity> borrowings = repository.findByMonthAndYear(startDate, endDate);

        return borrowings.stream().map(BorrowingMapper::fromEntity).toList();
    }

    @Override
    public Borrowing findById(UUID uuid) {
        Optional<BorrowingEntity> borrowing = repository.findById(uuid.toString());
        borrowing.orElseThrow(() -> new RuntimeException("")); // TODO: Criar exception personalizada
        return fromEntity(borrowing.get());
    }

    @Override
    public BigDecimal sumOfRemainingPayment() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("paid").is(false)),
                Aggregation.group().sum("value").as("total")
        );
        AggregationResults<Document> result = mongoTemplate.aggregate(agg, borrowingsCollection, Document.class);
        Document total = result.getUniqueMappedResult();

        return total != null ? new BigDecimal(total.get("total").toString()) : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumOfRemainingPayment(Month month, int year) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = LocalDate.of(year, month, 1).plusMonths(1);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("paid").is(false).andOperator(Criteria.where("date").gte(startOfMonth).lt(endOfMonth))),
                Aggregation.group().sum("value").as("total")
        );
        AggregationResults<Document> result = mongoTemplate.aggregate(agg, borrowingsCollection, Document.class);
        Document total = result.getUniqueMappedResult();

        return total != null ? new BigDecimal(total.get("total").toString()) : BigDecimal.ZERO;
    }
}
