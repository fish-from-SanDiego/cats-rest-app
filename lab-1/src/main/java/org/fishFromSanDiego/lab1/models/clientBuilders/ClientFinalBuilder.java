package org.fishFromSanDiego.lab1.models.clientBuilders;

import lombok.NonNull;
import org.fishFromSanDiego.lab1.models.Client;

import java.util.UUID;

public interface ClientFinalBuilder {
    ClientFinalBuilder withAddress(@NonNull String address);

    ClientFinalBuilder withPassport(@NonNull UUID passportId);

    Client build();
}
