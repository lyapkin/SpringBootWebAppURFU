package ru.liapkin.springbootwebappurfu.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.liapkin.springbootwebappurfu.dto.ArtistDto;
import ru.liapkin.springbootwebappurfu.service.ArtistService;
import ru.liapkin.springbootwebappurfu.service.LoggingService;

import java.util.Map;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final LoggingService loggingService;

    public ArtistController(ArtistService artistService,
                            LoggingService loggingService) {
        this.artistService = artistService;
        this.loggingService = loggingService;
    }

    @GetMapping({"/list", "/", ""})
    public ModelAndView getAllArtists() {

        ModelAndView mav = new ModelAndView("list-artists");
        mav.addObject("artists", artistService.findAll());
        loggingService.logging("Пользователь запросил список артистов");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addArtistForm() {
        ModelAndView mav = new ModelAndView("add-artist-form");
        ArtistDto artistDto = new ArtistDto();
        mav.addObject("artist", artistDto);
        loggingService.logging("Пользователь хочет добавить нового артиста");
        return mav;
    }

    @PostMapping("/save")
    public String saveArtist(@Valid @ModelAttribute("artist") ArtistDto artistDto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "add-artist-form";
        }
        artistService.save(artistDto);
        loggingService.logging("Пользователь сохранил артиста с именем " + artistDto.getName());
        return "redirect:/artists/list";
    }

    @GetMapping("/update")
    public ModelAndView showUpdateForm(@RequestParam long artistId) {
        ModelAndView mav = new ModelAndView("add-artist-form");
        ArtistDto artistDto = artistService.findById(artistId);
        mav.addObject("artist", artistDto);
        loggingService.logging("Пользователь хочет изменить артиста с именем " + artistDto.getName());
        return mav;
    }

    @GetMapping("/delete")
    public String deleteArtist(@RequestParam long artistId) {
        artistService.deleteById(artistId);
        loggingService.logging("Пользователь удалил артиста с id " + artistId);
        return "redirect:/artists/list";
    }

    @GetMapping("/revenue")
    public ModelAndView getRevenue(@RequestParam long artistId) {
        ArtistDto artistDto = artistService.findById(artistId);
        Map<String, Double> songRevenues = artistService.getArtistRevenue(artistId);
        ModelAndView mav = new ModelAndView("revenue");
        mav.addObject("name", artistDto.getName());
        mav.addObject("revenues", songRevenues);
        mav.addObject("totalRevenue", artistService.calculateTotalRevenue(artistId));
        return mav;
    }

}

