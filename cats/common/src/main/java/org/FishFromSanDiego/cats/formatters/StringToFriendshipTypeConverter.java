package org.FishFromSanDiego.cats.formatters;

import org.FishFromSanDiego.cats.models.FriendshipType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToFriendshipTypeConverter implements Converter<String, FriendshipType> {
    @Override
    public FriendshipType convert(String source) {
        return FriendshipType.valueOf(source.toUpperCase());
    }
}
