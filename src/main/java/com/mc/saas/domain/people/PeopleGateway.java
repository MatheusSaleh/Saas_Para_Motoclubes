package com.mc.saas.domain.people;

import java.util.List;

public interface PeopleGateway {

    People save(People people);

    People findById(Long id);

    List<People> findAll();

    void deleteById(Long id);

    People update(People people);
}
