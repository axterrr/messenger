package ua.edu.ukma.hibskyi.messenger.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.exception.ConflictException;
import ua.edu.ukma.hibskyi.messenger.repository.ChatRepository;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;

@Component
@AllArgsConstructor
public class MessageValidator extends BaseValidatorImpl<MessageEntity, MessageView, String> {

    private UserRepository userRepository;
    private ChatRepository chatRepository;

    @Override
    public void validateForCreate(MessageView view) {
        super.validateForCreate(view);

        if (!chatRepository.existsById(view.getChatId())) {
            throw new ConflictException("Cannot create message in non existing chat");
        }
        if (!userRepository.existsById(view.getSenderId())) {
            throw new ConflictException("Cannot create message with non existing sender");
        }

        if (!chatRepository.existsByIdAndUsersId(view.getChatId(), view.getSenderId())) {
            throw new ConflictException("Sender must be a member of the chat");
        }
    }
}
