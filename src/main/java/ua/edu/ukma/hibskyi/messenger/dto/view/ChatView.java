package ua.edu.ukma.hibskyi.messenger.dto.view;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatView {

    @Size(max = 30, message = "Chat name is too large")
    @NotBlank(message = "Chat name cannot be blank")
    private String name;

    @NotEmpty(message = "Chat must contain at least 1 user")
    private List<String> usersIds;
}
