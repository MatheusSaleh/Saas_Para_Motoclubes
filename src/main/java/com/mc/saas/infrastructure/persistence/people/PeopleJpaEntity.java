package com.mc.saas.infrastructure.persistence.people;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "pessoa")
@Getter
public class PeopleJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", nullable = false)
    private String fullName;

    @Column(name = "foto")
    private String photo;
}
