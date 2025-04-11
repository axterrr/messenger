package ua.edu.ukma.hibskyi.messenger.service;

import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.UserView;

public interface UserService extends BaseService<UserView, UserResponse, String> {

    UserResponse getByIdWithChats(String id);
}
