package ua.edu.ukma.hibskyi.messenger.controller.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthPageController {

    private AuthService authService;

    @GetMapping("/login")
    public String login() {
        if (authService.isAuthenticated()) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        if (authService.isAuthenticated()) {
            return "redirect:/";
        }
        return "auth/register";
    }
}
