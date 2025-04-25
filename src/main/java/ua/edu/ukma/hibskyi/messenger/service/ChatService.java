package ua.edu.ukma.hibskyi.messenger.service;

import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;

public interface ChatService extends BaseService<ChatView, ChatResponse, String> {

    ChatResponse getByIdWithMessages(String id);

    void leaveChat(String chatId);
}
