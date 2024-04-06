package org.FishFromSanDiego.cats.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@ToString
public class UserDto {
    private long id;
    @NonNull
    private String firstName;
    private String secondName;
    private LocalDate birthDate;
}
