package com.mc.saas.infrastructure.persistence.payment;

import com.mc.saas.infrastructure.persistence.member.MemberJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamento")
@Getter
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "membro_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private MemberJpaEntity member;

    @Column(name = "data_pagamento")
    private LocalDate paymentDate;

    @Column(name = "mes_referencia")
    private String monthYearReference;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "forma_pagamento")
    private String paymentMethod;

    @Column(name = "situacao")
    private String paymentStatus;

    @Column(name = "data_vencimento")
    private LocalDate dueDate;
}
