package ru.liapkin.springbootwebappurfu.service;

import jakarta.persistence.EntityNotFoundException;
import ru.liapkin.springbootwebappurfu.dto.ArtistDto;

import java.util.List;
import java.util.Map;

public interface ArtistService {

    List<ArtistDto> findAll();

    ArtistDto findById(long id) throws EntityNotFoundException;

    void save(ArtistDto artistDto);

    void deleteById(long id);

    Map<String, Double> getArtistRevenue(long artistId);

    double calculateTotalRevenue(long artistId);
}
