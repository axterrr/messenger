package ua.edu.ukma.hibskyi.messenger.controller.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;
import ua.edu.ukma.hibskyi.messenger.service.UserService;

@Controller
@RequestMapping
@AllArgsConstructor
public class ChatPageController {

    private AuthService authService;
    private UserService userService;
    private ChatService chatService;

    @GetMapping
    public String chats(@RequestParam(name = "chat", required = false) String selectedChat, Model model) {
        String currentUserId = authService.getAuthenticatedUserId();
        model.addAttribute("currentUser", userService.getByIdWithChats(currentUserId));
        if (selectedChat != null) {
            model.addAttribute("activeChat", chatService.getByIdWithMessages(selectedChat));
        }
        return "chat";
    }
}
