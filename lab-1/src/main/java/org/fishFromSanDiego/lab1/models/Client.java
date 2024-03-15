package org.fishFromSanDiego.lab1.models;

import lombok.NonNull;
import org.fishFromSanDiego.lab1.models.clientBuilders.ClientFinalBuilder;
import org.fishFromSanDiego.lab1.models.clientBuilders.NameBuilder;

import java.util.Optional;

public record Client(@NonNull String name, @NonNull String surname, String address, Optional<Integer> passportId) {

    public static NameBuilder builder() {
        return new ClientBuilder();
    }

    private static final class ClientBuilder implements NameBuilder, ClientFinalBuilder {
        String _name;
        String _surname;
        String _address;
        int _passportId;


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
        public ClientFinalBuilder withPassport(int passportId) {
            _passportId = passportId;
            return this;
        }

        @Override
        public Client build() {
            return new Client(_name, _surname, _address, Optional.of(_passportId));
        }
    }
}
