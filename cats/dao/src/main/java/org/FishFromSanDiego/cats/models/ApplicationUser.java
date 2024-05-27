package org.FishFromSanDiego.cats.models;


import jakarta.persistence.*;
import lombok.*;
import org.FishFromSanDiego.cats.dto.UserDto;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "application_users")
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleType userRoleType;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "applicationUser")
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private User user;

    public static ApplicationUser fromDto(UserDto dto) {
        return ApplicationUser.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .userRoleType(UserRoleType.ROLE_USER)
                .build();
    }
}
