package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.UserView;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.service.UserService;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserEntity, UserView, UserResponse, String> implements UserService {

    @Override
    public UserResponse getByIdWithChats(String id) {
        UserEntity entity = getEntity(id);
        validator.validateForView(entity);
        Hibernate.initialize(entity.getChats());
        return mapper.mapToResponse(entity);
    }
}
