package com.mc.saas.infrastructure.persistence.user;

import com.mc.saas.domain.user.User;
import com.mc.saas.domain.user.UserGateway;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {

    private final UserJpaRepository repository;

    @Override
    @Transactional
    public User save(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        if (user.getId() != null) {
            entity = repository.findById(user.getId()).orElse(entity);
        }
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setActive(user.isActive());
        entity.setPhoto(user.getPhoto());
        return toDomain(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return repository.findByEmailIgnoreCase(email).map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    @Transactional
    public User updatePhoto(Long id, String photo) {
        UserJpaEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id));
        entity.setPhoto(photo);
        return toDomain(repository.save(entity));
    }

    private User toDomain(UserJpaEntity e) {
        return new User(
                e.getId(),
                e.getName(),
                e.getEmail(),
                e.getPasswordHash(),
                e.isActive(),
                e.getPhoto(),
                e.getCreatedAt()
        );
    }
}
