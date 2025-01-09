package pedro.almeida.financialcontrol.application.dtos.response;

import pedro.almeida.financialcontrol.domain.models.TransactionCategory;

import java.util.UUID;

public record TransactionCategoryResponseDTO(UUID id, String name, String description) {
    public TransactionCategoryResponseDTO(TransactionCategory transactionCategory) {
        this(
                transactionCategory.getId(),
                transactionCategory.getName(),
                transactionCategory.getDescription()
        );
    }

    @Override
    public String toString() {
        return "{ \"id\": \"" + id + "\", \"name\": \"" + name + "\", \"description\": \"" + description + "\" }";
    }
}
