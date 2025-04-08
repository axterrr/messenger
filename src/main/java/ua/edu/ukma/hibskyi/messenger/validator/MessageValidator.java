package ua.edu.ukma.hibskyi.messenger.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.exception.ValidationException;
import ua.edu.ukma.hibskyi.messenger.repository.ChatRepository;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;

@Component
@AllArgsConstructor
public class MessageValidator extends BaseValidatorImpl<MessageEntity, String> {

    private UserRepository userRepository;
    private ChatRepository chatRepository;

    @Override
    public void validateForCreate(MessageEntity entity) {
        super.validateForCreate(entity);

        String chatId = entity.getChat().getId();
        String senderId = entity.getSender().getId();

        if (!chatRepository.existsById(chatId)) {
            throw new ValidationException("Cannot create message in non existing chat");
        }
        if (!userRepository.existsById(senderId)) {
            throw new ValidationException("Cannot create message with non existing sender");
        }

        if (!chatRepository.existsByIdAndUsersId(chatId, senderId)) {
            throw new ValidationException("Sender must be a member of the chat");
        }
    }
}
