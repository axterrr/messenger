package ua.edu.ukma.hibskyi.messenger.model.response;

import jakarta.validation.constraints.Email;
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
public class UserResponse {

    @NotBlank(message = "User phone number cannot be blank")
    @Size(min = 10, max = 15, message = "Invalid user phone number size")
    private String phone;

    @NotBlank(message = "User email cannot be blank")
    @Email(message = "User email is in wrong format")
    @Size(max = 254, message = "User email is too large")
    private String email;

    @NotBlank(message = "User name cannot be blank")
    @Size(max = 30, message = "User name is too large")
    private String name;

    @Size(max = 150, message = "User description is too large")
    private String description;
}
