package ru.liapkin.springbootwebappurfu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liapkin.springbootwebappurfu.entity.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findArtistByName(String name);

    List<Artist> findArtistsByNameIn(List<String> artists);
}
