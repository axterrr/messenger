package ua.edu.ukma.hibskyi.messenger.service.implementation;

import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.UserView;
import ua.edu.ukma.hibskyi.messenger.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, UserView, UserResponse, String> implements UserService {
}
