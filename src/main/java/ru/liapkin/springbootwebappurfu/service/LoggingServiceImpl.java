package ru.liapkin.springbootwebappurfu.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.liapkin.springbootwebappurfu.dto.LogDto;
import ru.liapkin.springbootwebappurfu.entity.Log;
import ru.liapkin.springbootwebappurfu.repository.LogRepository;
import ru.liapkin.springbootwebappurfu.security.CustomUserDetails;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoggingServiceImpl implements LoggingService {

    private final LogRepository logRepository;

    public LoggingServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<LogDto> findAll() {
        return logRepository.findAll().stream().map(this::mapToLogDto).toList();
    }

    @Override
    public void logging(String info) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Log log = new Log();
        if (userDetails != null) {
            log.setUserEmail(userDetails.getEmail());
        }
        log.setDate(LocalDateTime.now());
        log.setInfo(info);
        logRepository.save(log);
    }

    private LogDto mapToLogDto(Log log) {
        LogDto logDto = new LogDto();
        logDto.setId(log.getId());
        logDto.setDate(log.getDate().toString());
        logDto.setUser(log.getUserEmail());
        logDto.setInfo(log.getInfo());
        return logDto;
    }
}
