package com.mc.saas.infrastructure.persistence.moto;

import com.mc.saas.infrastructure.persistence.member.MemberJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "moto")
@Getter
@Setter
public class MotoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "membro_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberJpaEntity member;

    @Column(name = "marca", nullable = false, length = 80)
    private String brand;

    @Column(name = "modelo", nullable = false, length = 120)
    private String model;

    @Column(name = "ano_fabricacao")
    private Integer manufactureYear;

    @Column(name = "placa", length = 10)
    private String licensePlate;

    @Column(name = "cor", length = 40)
    private String color;

    @Column(name = "cilindradas")
    private Integer displacementCc;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String notes;
}
