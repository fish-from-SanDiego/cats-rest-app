package org.FishFromSanDiego.cats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@ToString
public class UserUpdateDto {
    @NotBlank(message = "first name should not be blank (empty)")
    private String firstName;

    @NotBlank(message = "second name should not be blank (empty)")
    private String secondName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
}
