package ua.edu.ukma.hibskyi.messenger.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.hibskyi.messenger.model.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatController {

    private ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity<ChatEntity> getChat(@PathVariable UUID id) {
        return new ResponseEntity<>(chatService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> createChat(@Valid @RequestBody ChatEntity chat) {
        return new ResponseEntity<>(chatService.create(chat), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateChat(@PathVariable UUID id, @Valid @RequestBody ChatEntity chat) {
        chatService.update(id, chat);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable UUID id) {
        chatService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
