package ru.liapkin.springbootwebappurfu.service;

import ru.liapkin.springbootwebappurfu.dto.LogDto;

import java.util.List;

public interface LoggingService {

    void logging(String info);

    List<LogDto> findAll();
}
