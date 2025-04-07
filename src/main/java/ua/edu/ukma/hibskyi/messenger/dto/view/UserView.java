package ua.edu.ukma.hibskyi.messenger.dto.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserView {

    private String phone;

    private String email;

    private String name;

    private String description;
}
