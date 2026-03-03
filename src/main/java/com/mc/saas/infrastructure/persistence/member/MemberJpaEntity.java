package com.mc.saas.infrastructure.persistence.member;

import com.mc.saas.infrastructure.persistence.people.PeopleJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "membro")
@Getter
public class MemberJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "pessoa_id")
    @OneToOne(cascade = CascadeType.ALL)
    private PeopleJpaEntity people;

    @JoinColumn(name = "conjuge_id")
    @OneToOne(cascade = CascadeType.ALL)
    private PeopleJpaEntity spouse;

    private String cpf;

    private String rg;

    @Column(name = "pais")
    private String country;

    @Column(name = "ocupacao")
    private String occupation;

    @Column(name = "ativo")
    private boolean isActive;

    @Column(name = "em_dia")
    private boolean upToDate;

    @Column(name = "nome_guerra")
    private String alias;

    @Column(name = "tipo_sanguineo")
    private String bloodType;

    @Column(name = "telefone")
    private String phone;

    private String email;

    @Column(name = "autorizou_uso_midias")
    private boolean authorizedTheUseOfMedia;
}
