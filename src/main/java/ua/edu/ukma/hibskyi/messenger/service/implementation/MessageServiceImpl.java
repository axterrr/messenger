package ua.edu.ukma.hibskyi.messenger.service.implementation;

import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.mapper.BaseMapperImpl;
import ua.edu.ukma.hibskyi.messenger.model.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.model.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.repository.BaseRepository;
import ua.edu.ukma.hibskyi.messenger.service.MessageService;

@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageEntity, MessageView, MessageResponse, String> implements MessageService {

    public MessageServiceImpl(BaseRepository<MessageEntity, String> repository,
                           BaseMapperImpl<MessageEntity, MessageView, MessageResponse> mapper) {
        super(repository, mapper);
    }
}
