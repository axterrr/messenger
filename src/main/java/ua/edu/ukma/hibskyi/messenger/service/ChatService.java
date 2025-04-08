package ua.edu.ukma.hibskyi.messenger.service;

import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;

import java.util.List;

public interface ChatService extends BaseService<ChatView, ChatResponse, String> {

    List<ChatResponse> getByUser(String userId);
}
