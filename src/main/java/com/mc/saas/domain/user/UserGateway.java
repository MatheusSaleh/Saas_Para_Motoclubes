package com.mc.saas.domain.user;

import java.util.Optional;

public interface UserGateway {

    User save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User updatePhoto(Long id, String photo);
}
