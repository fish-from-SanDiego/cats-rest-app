package org.fishFromSanDiego.lab1.models.clientBuilders;

import lombok.NonNull;

public interface NameBuilder {
    ClientFinalBuilder withFullName(@NonNull String Name, @NonNull String Surname);
}
