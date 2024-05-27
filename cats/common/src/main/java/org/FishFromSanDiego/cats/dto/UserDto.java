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
    @JsonView(UserView.ForAdmin.class)
    private long id;

    @NotBlank(message = "username should not be blank (empty)")
    @JsonView({UserView.ForUser.class, UserView.ForAdmin.class, UserView.Register.class})
    private String username;

    @NotBlank(message = "password should not be blank (empty)")
    @JsonView(UserView.Register.class)
    private String password;

    @NotBlank(message = "first name should not be blank (empty)")
    @JsonView({UserView.ForUser.class, UserView.ForAdmin.class, UserView.Register.class})
    private String firstName;

    @NotBlank(message = "second name should not be blank (empty)")
    @JsonView({UserView.ForUser.class, UserView.ForAdmin.class, UserView.Register.class})
    private String secondName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonView({UserView.ForUser.class, UserView.ForAdmin.class, UserView.Register.class})
    private LocalDate birthDate;
}
