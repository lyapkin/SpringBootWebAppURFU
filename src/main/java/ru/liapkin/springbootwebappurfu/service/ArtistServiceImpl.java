package ru.liapkin.springbootwebappurfu.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.liapkin.springbootwebappurfu.dto.ArtistDto;
import ru.liapkin.springbootwebappurfu.entity.Artist;
import ru.liapkin.springbootwebappurfu.entity.Song;
import ru.liapkin.springbootwebappurfu.repository.ArtistRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<ArtistDto> findAll() {
        return artistRepository.findAll().stream().map(this::mapToArtistDto).toList();
    }

    @Override
    public ArtistDto findById(long id) throws EntityNotFoundException {
        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isPresent()) {
            return mapToArtistDto(artist.get());
        }
        throw new EntityNotFoundException("Записи с таким идентификатором не существует");
    }

    @Override
    public void save(ArtistDto artistDto) {

        Artist artist = new Artist();
        artist.setName(artistDto.getName());
        if (artistDto.getId() != null) {
            artist.setId(artistDto.getId());
        }
        artistRepository.save(artist);

    }

    @Override
    public void deleteById(long id) {
        artistRepository.deleteById(id);
    }

    @Override
    public Map<String, Double> getArtistRevenue(long artistId) {
        Optional<Artist> artistOpt = artistRepository.findById(artistId);
        if (artistOpt.isEmpty()) {
            throw new RuntimeException("Исполнителя не существует");
        }
        List<Song> songs = artistOpt.get().getSongs();
        return songs.stream().collect(Collectors.toMap(Song::getTitle, s -> s.getListening()*s.getPrice()));
    }

    @Override
    public double calculateTotalRevenue(long artistId) {
        Optional<Artist> artistOpt = artistRepository.findById(artistId);
        if (artistOpt.isEmpty()) {
            throw new RuntimeException("Исполнителя не существует");
        }
        List<Double> songs = artistOpt.get().getSongs().stream().map(s -> s.getPrice()*s.getListening()).toList();
        return songs.stream().reduce(0d, Double::sum);
    }

    private ArtistDto mapToArtistDto(Artist artist) {
        ArtistDto artistDto = new ArtistDto();
        artistDto.setName(artist.getName());
        artistDto.setId(artist.getId());
        return artistDto;
    }
}
