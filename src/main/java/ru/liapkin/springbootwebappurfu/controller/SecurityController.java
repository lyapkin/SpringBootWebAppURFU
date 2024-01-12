package ru.liapkin.springbootwebappurfu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.liapkin.springbootwebappurfu.dto.UserDto;
import ru.liapkin.springbootwebappurfu.service.UserService;

import jakarta.validation.Valid;


@Controller
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult bindingResult) {

        if (userService.emailExists(userDto.getEmail())) {
            bindingResult.rejectValue("email", null, "На этот адрес электронной почты уже зарегистрирована учетная запись");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }


}
