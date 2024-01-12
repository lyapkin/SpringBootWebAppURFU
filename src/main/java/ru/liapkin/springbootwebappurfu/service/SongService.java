package ru.liapkin.springbootwebappurfu.service;

import ru.liapkin.springbootwebappurfu.dto.SongDto;

import java.util.List;

public interface SongService {

    List<SongDto> findAll();

    List<SongDto> findAllByArtistId(long artistId);

    List<SongDto> findAllByGuestArtistsId(long artistId);

    SongDto findById(long songId);

    void deleteById(long songId);

    void save(SongDto songDto);

}
