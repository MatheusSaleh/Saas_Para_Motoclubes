package com.mc.saas.domain.financial;

import java.util.List;
import java.util.Optional;

public interface PaymentGateway {

    Payment save(Payment payment);

    List<Payment> findAll();

    Optional<Payment> findById(Long id);

    void delete(Payment payment);

    Payment update(Payment payment);
}
