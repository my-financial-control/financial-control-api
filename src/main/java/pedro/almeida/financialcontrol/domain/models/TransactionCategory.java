package pedro.almeida.financialcontrol.domain.models;

import java.util.UUID;

public class TransactionCategory {
    private final UUID id;
    private final String name;
    private final String description;
    private final TransactionType type;

    public TransactionCategory(UUID id, String name, String description, TransactionType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getType() {
        return type;
    }
}
