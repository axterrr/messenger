package ua.edu.ukma.hibskyi.messenger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class MessageEntity implements Identifiable<String> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "content", nullable = false)
    @NotBlank(message = "Message content cannot be blank")
    @Size(max = 1000, message = "Message content is too large")
    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent_at", nullable = false, updatable = false)
    private LocalDateTime sentAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id", nullable = false, updatable = false)
    @NotNull(message = "Message must have a chat")
    private ChatEntity chat;

    @ManyToOne
    @JoinColumn(name = "sender_id", updatable = false)
    @NotNull(message = "Message must have a sender")
    private UserEntity sender;
}
