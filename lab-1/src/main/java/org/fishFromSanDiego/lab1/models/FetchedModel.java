package org.fishFromSanDiego.lab1.models;

import java.util.UUID;

public record FetchedModel<TModel>(TModel value, UUID id) {

}
