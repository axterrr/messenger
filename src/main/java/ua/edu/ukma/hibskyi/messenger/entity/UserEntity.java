package ua.edu.ukma.hibskyi.messenger.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Identifiable<String> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "phone", unique = true, nullable = false)
    @NotBlank(message = "User phone number cannot be blank")
    @Size(min = 10, max = 15, message = "Invalid user phone number size")
    private String phone;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "User email is in wrong format")
    @Size(max = 254, message = "User email is too large")
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 30, message = "Usename is too large")
    private String username;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "User name cannot be blank")
    @Size(max = 30, message = "User name is too large")
    private String name;

    @Column(name = "description")
    @Size(max = 150, message = "User description is too large")
    private String description;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "User password hash cannot be blank")
    private String passwordHash;

    @ManyToMany
    @JoinTable(
        name = "users_chats",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ChatEntity> chats;

    @OneToMany(mappedBy = "sender", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MessageEntity> messages;
}
