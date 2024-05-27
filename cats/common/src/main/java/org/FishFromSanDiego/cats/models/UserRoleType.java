package org.FishFromSanDiego.cats.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

public enum UserRoleType implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
