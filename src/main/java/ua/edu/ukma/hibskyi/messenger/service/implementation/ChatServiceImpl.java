package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.repository.ChatRepository;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl extends BaseServiceImpl<ChatEntity, ChatView, ChatResponse, String> implements ChatService {

    private ChatRepository chatRepository;

    @Override
    public List<ChatResponse> getByUser(String userId) {
        return mapper.mapToResponse(chatRepository.findByUsersId(userId));
    }
}
