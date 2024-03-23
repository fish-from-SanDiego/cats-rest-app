package org.fishFromSanDiego.lab1.models.clientBuilders;

import org.fishFromSanDiego.lab1.models.Client;

/**
 * The interface Client final builder.
 */
public interface ClientFinalBuilder {
    /**
     * With address client final builder.
     *
     * @param address the address
     * @return the client final builder
     */
    ClientFinalBuilder withAddress(String address);

    /**
     * With passport client final builder.
     *
     * @param passportId the passport id
     * @return the client final builder
     */
    ClientFinalBuilder withPassport(int passportId);

    /**
     * Build client.
     *
     * @return the client
     */
    Client build();
}
