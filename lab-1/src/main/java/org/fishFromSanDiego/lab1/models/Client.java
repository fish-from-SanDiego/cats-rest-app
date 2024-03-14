package org.fishFromSanDiego.lab1.models;

import lombok.NonNull;
import org.fishFromSanDiego.lab1.models.clientBuilders.ClientFinalBuilder;
import org.fishFromSanDiego.lab1.models.clientBuilders.NameBuilder;

import java.util.UUID;

public record Client(String name, String surname, String address, UUID passportId) {

    public Client {
        java.util.Objects.requireNonNull(name);
        java.util.Objects.requireNonNull(surname);
    }

    public static NameBuilder builder() {
        return new ClientBuilder();
    }

    private static final class ClientBuilder implements NameBuilder, ClientFinalBuilder {
        String _name;
        String _surname;
        String _address;
        UUID _passportId;


        @Override
        public ClientFinalBuilder withFullName(@NonNull String name, @NonNull String surname) {
            _name = name;
            _surname = surname;
            return this;
        }

        @Override
        public ClientFinalBuilder withAddress(@NonNull String address) {
            _address = address;
            return this;
        }

        @Override
        public ClientFinalBuilder withPassport(@NonNull UUID passportId) {
            _passportId = passportId;
            return this;
        }

        @Override
        public Client build() {
            return new Client(_name, _surname, _address, _passportId);
        }
    }
}
