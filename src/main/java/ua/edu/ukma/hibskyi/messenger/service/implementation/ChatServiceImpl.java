package ua.edu.ukma.hibskyi.messenger.service.implementation;

import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.mapper.BaseMapperImpl;
import ua.edu.ukma.hibskyi.messenger.model.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.model.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.repository.BaseRepository;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;

@Service
public class ChatServiceImpl extends BaseServiceImpl<ChatEntity, ChatView, ChatResponse, String> implements ChatService {

    public ChatServiceImpl(BaseRepository<ChatEntity, String> repository,
                           BaseMapperImpl<ChatEntity, ChatView, ChatResponse> mapper) {
        super(repository, mapper);
    }
}
