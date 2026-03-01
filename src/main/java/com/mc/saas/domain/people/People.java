package com.mc.saas.domain.people;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class People {

    private final Long id;

    private final String fullName;

    private final String photo;

    public People(Long id, String fullName, String photo) {
        this.id = id;
        this.fullName = fullName;
        this.photo = photo;
    }

    public static People create(String fullName, String photo) {
        return new People(null, fullName, photo);
    }

    private String validateName(String name){
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Nome não pode ser vazio!");
        }
        return name;
    }
}
