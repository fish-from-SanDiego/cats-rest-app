package org.fishFromSanDiego.lab1.models.clientBuilders;

import org.fishFromSanDiego.lab1.models.Client;

public interface ClientFinalBuilder {
    ClientFinalBuilder withAddress(String address);

    ClientFinalBuilder withPassport(int passportId);

    Client build();
}
