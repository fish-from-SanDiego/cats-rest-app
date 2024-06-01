package org.FishFromSanDiego.cats.formatters;

import org.FishFromSanDiego.cats.models.Colour;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToColourConverter implements Converter<String, Colour> {
    @Override
    public Colour convert(String source) {
        return Colour.valueOf(source.toUpperCase());
    }
}