package pedro.almeida.financialcontrol.domain.services.interfaces;

import pedro.almeida.financialcontrol.domain.models.Receipt;

import java.util.UUID;

public interface Storage {
    void save(Receipt file);

    Receipt find(UUID id);
}
