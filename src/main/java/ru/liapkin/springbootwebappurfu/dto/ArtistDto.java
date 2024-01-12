package ru.liapkin.springbootwebappurfu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {

    private Long id;

    @NotBlank(message = "Необходимо ввести имя")
    private String name;

    public void setName(String name) {
        this.name = name.trim();
    }
}
