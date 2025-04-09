package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.dto.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.repository.MessageRepository;
import ua.edu.ukma.hibskyi.messenger.service.MessageService;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl extends BaseServiceImpl<MessageEntity, MessageView, MessageResponse, String> implements MessageService {

    private MessageRepository messageRepository;

    @Override
    public List<MessageResponse> getByChatId(String chatId) {
        return mapper.mapToResponse(messageRepository.findByChatIdOrderBySentAt(chatId));
    }
}
