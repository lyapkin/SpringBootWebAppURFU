package ru.liapkin.springbootwebappurfu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liapkin.springbootwebappurfu.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
}
