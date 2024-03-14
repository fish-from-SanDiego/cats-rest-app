package org.fishFromSanDiego.lab1.services;

import java.util.Optional;

public interface LoginService<TService> {
    Optional<TService> tryLogin(String password);
}
