package ua.edu.ukma.hibskyi.messenger.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private String id;

    private String content;

    private LocalDateTime sentAt;

    private ChatResponse chat;

    private UserResponse sender;
}
