package ua.edu.ukma.hibskyi.messenger.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.hibskyi.messenger.model.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.service.MessageService;

import java.util.UUID;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @GetMapping("/{id}")
    public ResponseEntity<MessageEntity> getMessage(@PathVariable UUID id) {
        return new ResponseEntity<>(messageService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> createMessage(@Valid @RequestBody MessageEntity message) {
        return new ResponseEntity<>(messageService.create(message), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateMessage(@PathVariable UUID id, @Valid @RequestBody MessageEntity message) {
        messageService.update(id, message);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable UUID id) {
        messageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
