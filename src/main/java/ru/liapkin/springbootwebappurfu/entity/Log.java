package ru.liapkin.springbootwebappurfu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "info", nullable = false)
    private String info;
}
