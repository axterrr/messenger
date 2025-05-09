package ua.edu.ukma.hibskyi.messenger.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatController {

    private ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getAllChats() {
        return new ResponseEntity<>(chatService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatResponse> getChat(@PathVariable String id) {
        return new ResponseEntity<>(chatService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createChat(@RequestBody ChatView chat) {
        return new ResponseEntity<>(chatService.create(chat), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateChat(@PathVariable String id, @RequestBody ChatView chat) {
        chatService.update(id, chat);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable String id) {
        chatService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/leave")
    public ResponseEntity<HttpStatus> leaveChat(@PathVariable String id) {
        chatService.leaveChat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{chatId}/users/{userId}")
    public ResponseEntity<HttpStatus> deleteUserFromChat(@PathVariable String chatId, @PathVariable String userId) {
        chatService.deleteUserFromChat(chatId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{chatId}/owner")
    public ResponseEntity<HttpStatus> changeOwner(@PathVariable String chatId, @RequestBody String userId) {
        chatService.changeOwner(chatId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
