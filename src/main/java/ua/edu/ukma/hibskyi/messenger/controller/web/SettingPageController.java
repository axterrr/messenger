package ua.edu.ukma.hibskyi.messenger.controller.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;
import ua.edu.ukma.hibskyi.messenger.service.UserService;

@Controller
@RequestMapping("/settings")
@AllArgsConstructor
public class SettingPageController {

    private AuthService authService;
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model) {
        String currentUserId = authService.getAuthenticatedUserId();
        model.addAttribute("currentUser", userService.getById(currentUserId));
        return "pages/settings/profile";
    }

    @GetMapping("/profile/edit")
    public String profileEdit(Model model) {
        String currentUserId = authService.getAuthenticatedUserId();
        model.addAttribute("currentUser", userService.getById(currentUserId));
        return "pages/settings/edit-profile";
    }

    @GetMapping("/account")
    public String account(Model model) {
        String currentUserId = authService.getAuthenticatedUserId();
        model.addAttribute("currentUser", userService.getById(currentUserId));
        return "pages/settings/account";
    }
}
