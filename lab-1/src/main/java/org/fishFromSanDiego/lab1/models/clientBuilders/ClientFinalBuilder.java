package org.fishFromSanDiego.lab1.models.clientBuilders;

import lombok.NonNull;
import org.fishFromSanDiego.lab1.models.Client;

public interface ClientFinalBuilder {
    ClientFinalBuilder withAddress(@NonNull String address);

    ClientFinalBuilder withPassport(int passportId);

    Client build();
}
