package ru.liapkin.springbootwebappurfu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liapkin.springbootwebappurfu.entity.Song;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findAllByArtistId(long artistId);

    List<Song> findAllByGuestArtistsId(long artistId);
}
