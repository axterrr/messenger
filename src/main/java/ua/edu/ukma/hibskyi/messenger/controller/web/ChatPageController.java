package ua.edu.ukma.hibskyi.messenger.controller.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;
import ua.edu.ukma.hibskyi.messenger.service.MessageService;

@Controller
@RequestMapping
@AllArgsConstructor
public class ChatPageController {

    private AuthService authService;
    private ChatService chatService;
    private MessageService messageService;

    @GetMapping
    public String chats(@RequestParam(name = "chat", required = false) String selectedChat, Model model) {
        UserResponse currentUser = authService.getAuthenticatedUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("chats", chatService.getByUser(currentUser.getId()));

        if (selectedChat != null) {
            model.addAttribute("activeChat", chatService.getById(selectedChat));
            model.addAttribute("activeChatMessages", messageService.getByChatId(selectedChat));
        }

        return "chat";
    }
}
