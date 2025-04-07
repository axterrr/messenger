package ua.edu.ukma.hibskyi.messenger.controller.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.edu.ukma.hibskyi.messenger.service.UserService;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserPageController {

    private final UserService userService;

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }
}
