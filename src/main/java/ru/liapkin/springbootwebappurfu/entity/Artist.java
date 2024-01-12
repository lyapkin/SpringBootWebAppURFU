package ru.liapkin.springbootwebappurfu.entity;

import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Song> songs = new ArrayList<>();

    @ManyToMany(mappedBy = "guestArtists", fetch = FetchType.LAZY)
    private List<Song> guestSongs = new ArrayList<>();

}
