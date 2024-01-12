package ru.liapkin.springbootwebappurfu.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.liapkin.springbootwebappurfu.dto.ArtistDto;
import ru.liapkin.springbootwebappurfu.dto.SongDto;
import ru.liapkin.springbootwebappurfu.service.ArtistService;
import ru.liapkin.springbootwebappurfu.service.LoggingService;
import ru.liapkin.springbootwebappurfu.service.SongService;

import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;
    private final ArtistService artistService;
    private final LoggingService loggingService;

    public SongController(SongService songService,
                          ArtistService artistService,
                          LoggingService loggingService) {
        this.songService = songService;
        this.artistService = artistService;
        this.loggingService = loggingService;
    }

    @GetMapping({"/list", "/", ""})
    public ModelAndView getAllSongs() {
        ModelAndView mav = new ModelAndView("list-songs");
        mav.addObject("songs", songService.findAll());
        loggingService.logging("Пользователь запросил список песен");
        return mav;
    }

    @GetMapping("/artist")
    public ModelAndView getArtistSongs(@RequestParam long artistId) {
        ModelAndView mav = new ModelAndView("list-songs");
        ArtistDto artistDto = artistService.findById(artistId);
        List<SongDto> songs = songService.findAllByArtistId(artistId);

        mav.addObject("songs", songs);
        mav.addObject("artist", artistDto.getName());
        mav.addObject("sentence", "Песни исполнителя");
        return mav;
    }

    @GetMapping("/guestArtist")
    public ModelAndView getGuestArtistSongs(@RequestParam long artistId) {
        ModelAndView mav = new ModelAndView("list-songs");
        ArtistDto artistDto = artistService.findById(artistId);
        List<SongDto> songs = songService.findAllByGuestArtistsId(artistId);

        mav.addObject("songs", songs);
        mav.addObject("artist", artistDto.getName());
        mav.addObject("sentence", "Гостевый песни исполнителя");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addSongForm() {
        ModelAndView mav = new ModelAndView("add-song-form");
        SongDto song = new SongDto();
        List<String> artists = artistService.findAll().stream().map(ArtistDto::getName).toList();
        mav.addObject("song", song);
        mav.addObject("artists", artists);
        loggingService.logging("Пользователь хочет добавить новую песню");
        return mav;
    }

    @PostMapping("/save")
    public String saveSong(@Valid @ModelAttribute("song") SongDto songDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:add-song-form";
        }
        songService.save(songDto);
        loggingService.logging("Пользователь сохранил песню с названием " + songDto.getTitle());
        return "redirect:/songs/list";
    }

    @GetMapping("/update")
    public ModelAndView showUpdateForm(@RequestParam Long songId) {
        ModelAndView mav = new ModelAndView("add-song-form");
        SongDto songDto = songService.findById(songId);
        List<String> artists = artistService.findAll().stream().map(ArtistDto::getName).toList();
        mav.addObject("song", songDto);
        mav.addObject("artists", artists);
        loggingService.logging("Пользователь хочет изменить песню с названием " + songDto.getTitle());
        return mav;
    }

    @GetMapping("/delete")
    public String deleteSong(@RequestParam Long songId) {
        songService.deleteById(songId);
        loggingService.logging("Пользователь удалил песню с id " + songId);
        return "redirect:/songs/list";
    }
}
