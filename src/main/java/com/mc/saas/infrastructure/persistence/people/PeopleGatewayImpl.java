package com.mc.saas.infrastructure.persistence.people;

import com.mc.saas.domain.people.People;
import com.mc.saas.domain.people.PeopleGateway;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleGatewayImpl implements PeopleGateway {

    private final PeopleJpaRepository repository;

    @Override
    @Transactional
    public People save(People people) {
        PeopleJpaEntity entity = new PeopleJpaEntity();
        entity.setFullName(people.getFullName());
        entity.setPhoto(people.getPhoto());
        return toDomain(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public People findById(Long id) {
        return repository.findById(id).map(this::toDomain).orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<People> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public People update(People people) {
        PeopleJpaEntity entity = repository.findById(people.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada: " + people.getId()));
        entity.setFullName(people.getFullName());
        entity.setPhoto(people.getPhoto());
        return toDomain(repository.save(entity));
    }

    private People toDomain(PeopleJpaEntity e) {
        return new People(e.getId(), e.getFullName(), e.getPhoto());
    }
}
