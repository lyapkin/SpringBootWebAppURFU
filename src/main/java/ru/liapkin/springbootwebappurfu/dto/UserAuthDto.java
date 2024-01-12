package ru.liapkin.springbootwebappurfu.dto;

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
public class UserAuthDto {

    private Long id;

    private String email;

    private List<String> roles = new ArrayList<>();

}
