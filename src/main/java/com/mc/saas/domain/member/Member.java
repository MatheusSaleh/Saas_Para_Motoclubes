package com.mc.saas.domain.member;

import com.mc.saas.domain.people.People;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class Member {

    private Long id;

    private People people;

    private People spouse;

    private String cpf;

    private String rg;

    private String country;

    private String occupation;

    private boolean isActive;

    private boolean upToDate;

    private String alias;

    private String bloodType;

    private String phone;

    private String email;

    private boolean authorizedTheUseOfMedia;

    public Member(Long id, People people, People spouse, String cpf, String rg, String country, String occupation, boolean isActive, boolean upToDate, String alias, String bloodType, String phone, String email, boolean authorizedTheUseOfMedia) {
        this.id = id;
        this.people = people;
        this.spouse = spouse;
        this.cpf = cpf;
        this.rg = rg;
        this.country = country;
        this.occupation = occupation;
        this.isActive = isActive;
        this.upToDate = upToDate;
        this.alias = alias;
        this.bloodType = bloodType;
        this.phone = phone;
        this.email = email;
        this.authorizedTheUseOfMedia = authorizedTheUseOfMedia;
    }

    public static Member create(People people, People spouse, String cpf, String rg, String country, String occupation, boolean isActive, boolean upToDate, String alias, String bloodType, String phone, String email, boolean authorizedTheUseOfMedia) {
        return new Member(null, people, spouse, cpf, rg, country, occupation, isActive, upToDate, alias, bloodType, phone, email, authorizedTheUseOfMedia);
    }
}
