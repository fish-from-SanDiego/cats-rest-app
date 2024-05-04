package org.FishFromSanDiego.cats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@ToString
public class UserDto {
    private long id;

    @NotBlank(message = "first name should not be blank (empty)")
    @JsonView(UserView.Request.class)
    private String firstName;

    @NotBlank(message = "second name should not be blank (empty)")
    @JsonView(UserView.Request.class)
    private String secondName;

    @JsonView(UserView.Request.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
}
