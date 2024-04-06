package org.FishFromSanDiego.cats.models;

import jakarta.persistence.*;
import lombok.*;
import org.FishFromSanDiego.cats.dto.CatDto;

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
@Table(name = "cats")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "breed")
    private String breed;
    @Column(name = "colour")
    private Colour colour;
    @ManyToOne(targetEntity = User.class, optional = false)
    @ToString.Exclude
    private User owner;
    @ManyToMany(targetEntity = Cat.class, cascade = CascadeType.ALL)
    @JoinTable(name = "cats_catfriends", joinColumns = @JoinColumn(name = "cat_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @Builder.Default
    @ToString.Exclude
    private List<Cat> friends = new ArrayList<>();

    public static Cat fromDto(CatDto catDto) {
        return Cat.builder()
                .birthDate(catDto.getBirthDate())
                .name(catDto.getName())
                .colour(catDto.getColour())
                .breed(catDto.getBreed())
                .build();
    }

    public void copyFromDto(CatDto catDto) {
        birthDate = catDto.getBirthDate();
        colour = catDto.getColour();
        name = catDto.getName();
        breed = catDto.getBreed();
    }

    public CatDto getDto() {
        return CatDto.builder()
                .birthDate(getBirthDate())
                .name(getName())
                .colour(getColour())
                .breed(getBreed())
                .ownerId(owner.getId())
                .id(id)
                .build();
    }
}
