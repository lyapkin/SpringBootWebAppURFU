package ru.liapkin.springbootwebappurfu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogDto {

    private long id;

    private String date;

    private String user;

    private String info;

}
