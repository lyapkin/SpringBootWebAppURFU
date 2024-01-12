package ru.liapkin.springbootwebappurfu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.liapkin.springbootwebappurfu.dto.UserAuthDto;
import ru.liapkin.springbootwebappurfu.dto.UserDto;
import ru.liapkin.springbootwebappurfu.enums.RoleTypes;
import ru.liapkin.springbootwebappurfu.service.LoggingService;
import ru.liapkin.springbootwebappurfu.service.UserService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LoggingService loggingService;

    public UserController(UserService userService,
                          LoggingService loggingService) {
        this.userService = userService;
        this.loggingService = loggingService;
    }


    @GetMapping({"/list", "/", ""})
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        loggingService.logging("Пользователь запросил список пльзователей");
        return "users";
    }

    @GetMapping("/change-roles")
    public ModelAndView showForm(@RequestParam long userId) {
        ModelAndView mav = new ModelAndView("change-role-form");
        UserAuthDto userAuthDto = userService.getUserAuthDtoById(userId);
        mav.addObject("user", userAuthDto);
        mav.addObject("roles", Arrays.stream(RoleTypes.values()).map(RoleTypes::getType).toList());
        return mav;

    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") UserAuthDto userAuthDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "change-role-form";
        }
        userService.saveChangeAuthUser(userAuthDto);
        loggingService.logging("Пользователь изменил роли у пользователя " + userAuthDto.getEmail());
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long userId) {
        userService.deleteById(userId);
        loggingService.logging("Пользователь с id " + userId + " удален");
        return "redirect:/users";
    }
}
