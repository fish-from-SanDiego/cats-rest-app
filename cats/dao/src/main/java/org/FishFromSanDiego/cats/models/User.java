package org.FishFromSanDiego.cats.models;

import jakarta.persistence.*;
import lombok.*;
import org.FishFromSanDiego.cats.dto.UserDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(
            targetEntity = Cat.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "owner")
    @Builder.Default
    @ToString.Exclude
    private List<Cat> cats = new ArrayList<>();

    public static User fromDto(UserDto userDto) {
        return User.builder()
                .birthDate(userDto.getBirthDate())
                .firstName(userDto.getFirstName())
                .secondName(userDto.getSecondName())
                .build();
    }

    public void copyFromDto(UserDto userDto) {
        birthDate = userDto.getBirthDate();
        firstName = userDto.getFirstName();
        secondName = userDto.getSecondName();
    }

    public UserDto getDto() {
        return UserDto.builder()
                .birthDate(birthDate)
                .firstName(firstName)
                .secondName(secondName)
                .build();
    }
}
