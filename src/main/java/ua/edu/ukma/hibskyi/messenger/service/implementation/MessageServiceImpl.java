package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.service.MessageService;

@Service
@Transactional
@AllArgsConstructor
public class MessageServiceImpl extends BaseServiceImpl<MessageEntity, MessageView, MessageResponse, String> implements MessageService {

    private SimpMessagingTemplate messagingTemplate;

    @Override
    public String create(MessageView view) {
        MessageEntity entity = createEntity(view);
        entity.getChat().setLastActionAt(entity.getSentAt());
        entity.getChat().setLastMessage(entity);

        MessageResponse response = mapper.mapToResponse(entity);
        messagingTemplate.convertAndSend("/topic/chat/" + view.getChatId(), response);
        entity.getChat().getUsers().forEach(user ->
            messagingTemplate.convertAndSend("/topic/user/" + user.getId(), response)
        );
        return entity.getId();
    }

    @Override
    public void deleteById(String id) {
        MessageEntity entity = deleteEntity(id);
        ChatEntity chat = entity.getChat();
        if (chat.getLastMessage() == entity) {
            var messages = chat.getMessages();
            messages.remove(entity);
            chat.setLastMessage(messages.isEmpty() ? null : messages.getLast());
        }

        MessageResponse newLastMessage = mapper.mapToResponse(chat.getLastMessage());
        if (newLastMessage == null) {
            newLastMessage = MessageResponse.builder()
                .chat(ChatResponse.builder().id(chat.getId()).build())
                .content(null)
                .build();
        }
        MessageResponse finalNewLastMessage = newLastMessage;
        messagingTemplate.convertAndSend("/topic/chat/delete/" + chat.getId(), entity.getId());
        chat.getUsers().forEach(user ->
            messagingTemplate.convertAndSend("/topic/user/update-last-message/" + user.getId(), finalNewLastMessage)
        );
    }
}
