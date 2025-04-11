package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ukma.hibskyi.messenger.dto.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.service.MessageService;

@Service
@Transactional
@AllArgsConstructor
public class MessageServiceImpl extends BaseServiceImpl<MessageEntity, MessageView, MessageResponse, String> implements MessageService {
}
