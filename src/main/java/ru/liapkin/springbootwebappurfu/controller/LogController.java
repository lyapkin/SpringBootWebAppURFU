package ru.liapkin.springbootwebappurfu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.liapkin.springbootwebappurfu.service.LoggingService;

@Controller
@RequestMapping("/logs")
public class LogController {

    private final LoggingService loggingService;

    public LogController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping({"/list", "/", ""})
    public ModelAndView getLogs() {
        ModelAndView mav = new ModelAndView("list-logs");
        mav.addObject("logs", loggingService.findAll());
        return mav;
    }
}
