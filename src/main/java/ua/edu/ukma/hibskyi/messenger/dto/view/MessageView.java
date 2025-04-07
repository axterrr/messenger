package ua.edu.ukma.hibskyi.messenger.dto.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageView {

    private String content;

    private String chatId;

    private String senderId;
}
