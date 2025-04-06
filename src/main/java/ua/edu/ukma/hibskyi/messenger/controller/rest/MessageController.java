package ua.edu.ukma.hibskyi.messenger.controller.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ukma.hibskyi.messenger.model.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getAllMessages() {
        return new ResponseEntity<>(messageService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable String id) {
        return new ResponseEntity<>(messageService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@Valid @RequestBody MessageView message) {
        return new ResponseEntity<>(messageService.create(message), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateMessage(@PathVariable String id, @Valid @RequestBody MessageView message) {
        messageService.update(id, message);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable String id) {
        messageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
