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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
public class User {
    @Id
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne(targetEntity = ApplicationUser.class, optional = false)
    @ToString.Exclude
    private ApplicationUser applicationUser;

    @OneToMany(targetEntity = Cat.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
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

//    public static User fromUserRegisterDto(FullUserDto dto) {
//        return User.builder()
//                .birthDate(dto.getBirthDate())
//                .firstName(dto.getFirstName())
//                .secondName(dto.getSecondName())
//                .build();
//    }

    public User copyFromDto(UserDto userDto) {
        birthDate = userDto.getBirthDate();
        firstName = userDto.getFirstName();
        secondName = userDto.getSecondName();
        return this;
    }

    public UserDto getDto() {
        var applicationUserEntity = this.getApplicationUser();
        return UserDto.builder()
                .birthDate(birthDate)
                .firstName(firstName)
                .secondName(secondName)
                .id(id)
                .password(applicationUserEntity.getPassword())
                .username(applicationUserEntity.getUsername())
                .build();
    }
}
