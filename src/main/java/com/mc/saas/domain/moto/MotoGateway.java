package com.mc.saas.domain.moto;

import java.util.List;
import java.util.Optional;

public interface MotoGateway {

    Moto save(Moto moto);

    Moto findById(Long id);

    List<Moto> findAll();

    Optional<Moto> findOptionalById(Long id);

    void delete(Moto moto);

    Moto update(Moto moto);
}
