package org.fishFromSanDiego.lab1.services.abstractions;

import java.util.Optional;

/**
 * The interface Login service.
 *
 * @param <TService> the type parameter
 */
public interface LoginService<TService> {
    /**
     * Try login optional.
     *
     * @param password the password
     * @return the optional
     */
    Optional<TService> tryLogin(String password);
}
