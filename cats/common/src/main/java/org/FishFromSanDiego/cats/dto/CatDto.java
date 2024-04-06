package org.FishFromSanDiego.cats.dto;

import lombok.*;
import org.FishFromSanDiego.cats.models.Colour;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@ToString
public class CatDto {
    private long id;
    @NonNull
    private String name;
    private LocalDate birthDate;
    private String breed;
    private Colour colour;
    private long ownerId;

}
