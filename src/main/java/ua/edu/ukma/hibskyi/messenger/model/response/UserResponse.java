package ua.edu.ukma.hibskyi.messenger.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;

    private String phone;

    private String email;

    private String name;

    private String description;

//    private List<ChatResponse> chats;
}
