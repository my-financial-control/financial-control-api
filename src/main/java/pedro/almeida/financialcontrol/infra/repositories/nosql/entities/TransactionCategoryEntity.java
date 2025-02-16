package pedro.almeida.financialcontrol.infra.repositories.nosql.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;
import pedro.almeida.financialcontrol.domain.models.TransactionType;

import java.util.UUID;

@Document(collection = "categories")
public class TransactionCategoryEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private String type;

    public TransactionCategoryEntity() {
    }

    public TransactionCategoryEntity(String id, String name, String description, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public TransactionCategoryEntity(TransactionCategory transactionCategory) {
        this.id = transactionCategory.getId().toString();
        this.name = transactionCategory.getName();
        this.description = transactionCategory.getDescription();
    }

    public TransactionCategory toModel() {
        return new TransactionCategory(UUID.fromString(id), name, description, TransactionType.valueOf(type));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
