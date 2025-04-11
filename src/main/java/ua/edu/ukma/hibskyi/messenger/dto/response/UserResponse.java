package ua.edu.ukma.hibskyi.messenger.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;

    private String phone;

    private String username;

    private String email;

    private String name;

    private String description;

    private List<ChatResponse> chats;
}
