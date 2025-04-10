package ua.edu.ukma.hibskyi.messenger.dto.view;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserView {

    @NotBlank(message = "User phone number cannot be blank")
    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Invalid user phone number")
    private String phone;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 30, message = "Username is too large")
    private String username;

    @Email(message = "User email is in wrong format")
    @Size(max = 254, message = "User email is too large")
    private String email;

    @NotBlank(message = "User name cannot be blank")
    @Size(max = 30, message = "User name is too large")
    private String name;

    @Size(max = 150, message = "User description is too large")
    private String description;

    @NotBlank(message = "User password cannot be blank")
    @Size(min = 8, message = "Password length should be at least 8 symbols")
    private String password;

    @NotBlank(message = "Please, confirm a password")
    private String confirmPassword;
}
