package com.mc.saas.infrastructure.persistence.people;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleJpaRepository extends JpaRepository<PeopleJpaEntity, Long> {
}
