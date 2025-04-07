package ua.edu.ukma.hibskyi.messenger.dto.view;

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

    private String name;

    private List<String> usersIds;
}
