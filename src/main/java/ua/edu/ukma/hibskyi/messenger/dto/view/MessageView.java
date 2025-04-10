package ua.edu.ukma.hibskyi.messenger.dto.view;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageView {

    @NotBlank(message = "Message content cannot be blank")
    @Size(max = 1000, message = "Message content is too large")
    private String content;

    @NotBlank(message = "Message must have a chat")
    private String chatId;

    @NotBlank(message = "Message must have a sender")
    private String senderId;
}
