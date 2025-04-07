package ua.edu.ukma.hibskyi.messenger.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private String id;

    private String name;

//    private List<MessageResponse> messages;

//    private List<UserResponse> users;
}
