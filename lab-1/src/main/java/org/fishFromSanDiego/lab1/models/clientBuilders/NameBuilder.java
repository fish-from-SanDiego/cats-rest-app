package org.fishFromSanDiego.lab1.models.clientBuilders;

import lombok.NonNull;

/**
 * The interface Name builder.
 */
public interface NameBuilder {
    /**
     * With full name client final builder.
     *
     * @param Name    the name
     * @param Surname the surname
     * @return the client final builder
     */
    ClientFinalBuilder withFullName(@NonNull String Name, @NonNull String Surname);
}
