package ua.edu.ukma.hibskyi.messenger.service.implementation;

import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.mapper.BaseMapperImpl;
import ua.edu.ukma.hibskyi.messenger.model.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.model.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.UserView;
import ua.edu.ukma.hibskyi.messenger.repository.BaseRepository;
import ua.edu.ukma.hibskyi.messenger.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, UserView, UserResponse, String> implements UserService {

    public UserServiceImpl(BaseRepository<UserEntity, String> repository,
                              BaseMapperImpl<UserEntity, UserView, UserResponse> mapper) {
        super(repository, mapper);
    }
}
