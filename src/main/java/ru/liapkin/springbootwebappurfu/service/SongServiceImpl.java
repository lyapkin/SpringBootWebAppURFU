package ru.liapkin.springbootwebappurfu.service;

import org.springframework.stereotype.Service;
import ru.liapkin.springbootwebappurfu.dto.SongDto;
import ru.liapkin.springbootwebappurfu.entity.Artist;
import ru.liapkin.springbootwebappurfu.entity.Song;
import ru.liapkin.springbootwebappurfu.repository.ArtistRepository;
import ru.liapkin.springbootwebappurfu.repository.SongRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongServiceImpl(SongRepository songRepository,
                           ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<SongDto> findAll() {
        return songRepository.findAll().stream().map(this::mapToSongDto).toList();
    }

    @Override
    public List<SongDto> findAllByArtistId(long artistId) {
        return songRepository.findAllByArtistId(artistId).stream().map(this::mapToSongDto).toList();
    }

    @Override
    public List<SongDto> findAllByGuestArtistsId(long artistId) {
        return songRepository.findAllByGuestArtistsId(artistId).stream().map(this::mapToSongDto).toList();
    }

    @Override
    public SongDto findById(long songId) {
        Optional<Song> songOpt = songRepository.findById(songId);
        if (songOpt.isEmpty()) throw new RuntimeException("Такой песни не существует");
        return mapToSongDto(songOpt.get());
    }

    @Override
    public void deleteById(long songId) {
        songRepository.deleteById(songId);
    }

    @Override
    public void save(SongDto songDto) {
        Song song = new Song();
        if (songDto.getId() != null) {
            song.setId(songDto.getId());
        }
        song.setTitle(songDto.getTitle());
        Optional<Artist> artistOpt = artistRepository.findArtistByName(songDto.getArtistName());
        if (artistOpt.isEmpty()) {
            throw new RuntimeException("Такого артиста не существует");
        }
        song.setArtist(artistOpt.get());
        if (songDto.getGuestArtists() != null && !songDto.getGuestArtists().isEmpty()) {
            List<Artist> guestArtists = artistRepository.findArtistsByNameIn(songDto.getGuestArtists());
            song.setGuestArtists(guestArtists);
        }
        songRepository.save(song);
    }

    private SongDto mapToSongDto(Song song) {
        SongDto songDto = new SongDto();
        songDto.setTitle(song.getTitle());
        songDto.setArtistName(song.getArtist().getName());
        songDto.setRevenue(song.getPrice()*song.getListening());
        songDto.setGuestArtists(song.getGuestArtists().stream().map(Artist::getName).toList());
        songDto.setId(song.getId());
        return songDto;
    }
}
