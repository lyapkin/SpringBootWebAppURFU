package ru.liapkin.springbootwebappurfu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String artistName;

    private double revenue;

    private List<String> guestArtists = new ArrayList<>();

}
