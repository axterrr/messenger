package ua.edu.ukma.hibskyi.messenger.repository;

import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends BaseRepository<UserEntity, String> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhone(String phone);

    Optional<UserEntity> findByUsername(String username);
}
