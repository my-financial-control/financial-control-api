package pedro.almeida.financialcontrol.infra.repositories.nosql;

import org.springframework.stereotype.Repository;
import pedro.almeida.financialcontrol.domain.errors.NotFoundException;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;
import pedro.almeida.financialcontrol.domain.repositories.TransactionCategories;
import pedro.almeida.financialcontrol.infra.repositories.nosql.entities.TransactionCategoryEntity;
import pedro.almeida.financialcontrol.infra.repositories.nosql.interfaces.ITransactionCategoryNoSQLRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionCategoryNoSQLRepository implements TransactionCategories {

    private final ITransactionCategoryNoSQLRepository repository;

    public TransactionCategoryNoSQLRepository(ITransactionCategoryNoSQLRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionCategory findById(UUID id) {
        Optional<TransactionCategoryEntity> tc = repository.findById(id.toString());
        return tc.orElseThrow(() -> new NotFoundException("Categoria com id " + id.toString() + " não encontrada")).toModel();
    }

    @Override
    public List<TransactionCategory> findAll() {
        List<TransactionCategoryEntity> categories = repository.findAll();
        return categories.stream().map(TransactionCategoryEntity::toModel).toList();
    }
}
