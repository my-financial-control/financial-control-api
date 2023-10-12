package pedro.almeida.financialcontrol.domain.usecases;

import pedro.almeida.financialcontrol.domain.models.ParcelBorrowing;

import java.util.UUID;

public interface PayParcelBorrowing {

    void execute(UUID id, ParcelBorrowing parcel);

}
