package org.fishFromSanDiego.lab1.models;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.fishFromSanDiego.lab1.models.clientBuilders.ClientFinalBuilder;
import org.fishFromSanDiego.lab1.models.clientBuilders.NameBuilder;

import java.util.Optional;

/**
 * The type Client.
 */
public record Client(@NonNull String name, @NonNull String surname, String address, Optional<Integer> passportId) {

    /**
     * Is suspicious boolean.
     *
     * @return the boolean
     */
    public boolean isSuspicious() {
        return address == null || address.isEmpty() || passportId().isEmpty();
    }

    /**
     * Builder name builder.
     *
     * @return the name builder
     */
    public static NameBuilder builder() {
        return new ClientBuilder();
    }

    @NoArgsConstructor
    private static final class ClientBuilder implements NameBuilder, ClientFinalBuilder {
        private String _name;
        private String _surname;
        private String _address;
        private Optional<Integer> _passportId = Optional.empty();


        @Override
        public ClientFinalBuilder withFullName(@NonNull String name, @NonNull String surname) {
            _name = name;
            _surname = surname;
            return this;
        }

        @Override
        public ClientFinalBuilder withAddress(String address) {
            _address = address;
            return this;
        }

        @Override
        public ClientFinalBuilder withPassport(int passportId) {
            _passportId = Optional.of(passportId);
            return this;
        }

        @Override
        public Client build() {
            return new Client(_name, _surname, _address, _passportId);
        }
    }

    /**
     * Direct builder client final builder.
     *
     * @param builder the builder
     * @return the client final builder
     */
    public ClientFinalBuilder directBuilder(NameBuilder builder) {
        return passportId.isPresent()
                ? builder
                .withFullName(name, surname)
                .withAddress(address)
                .withPassport(passportId.get())
                : builder
                .withFullName(name, surname)
                .withAddress(address != null ? address : "");
    }
}
