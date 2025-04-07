package ua.edu.ukma.hibskyi.messenger.controller.rest;

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
}
