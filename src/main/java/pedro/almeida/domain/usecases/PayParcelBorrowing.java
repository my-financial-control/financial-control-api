package pedro.almeida.domain.usecases;

import pedro.almeida.domain.models.ParcelBorrowing;

import java.util.UUID;

public interface PayParcelBorrowing {

    void execute(UUID id, ParcelBorrowing parcel);

}
