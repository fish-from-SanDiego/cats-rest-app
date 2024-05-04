package org.FishFromSanDiego.cats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "cat's name should not be blank (empty)")
    @JsonView({CatView.PostRequest.class, CatView.PutRequest.class})
    private String name;

    @JsonView({CatView.PostRequest.class, CatView.PutRequest.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @JsonView({CatView.PostRequest.class, CatView.PutRequest.class})
    private String breed;

    @JsonView({CatView.PostRequest.class, CatView.PutRequest.class})
    private Colour colour;

    @JsonView(CatView.PostRequest.class)
    private long ownerId;

}
