package ua.edu.ukma.hibskyi.messenger.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private String id;

    private String name;

    private LocalDateTime lastActionAt;

    private UserResponse owner;

    private MessageResponse lastMessage;

    private List<MessageResponse> messages;

    private List<UserResponse> users;
}
