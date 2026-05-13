package com.mc.saas.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
public class User {

    private final Long id;

    private final String name;

    private final String email;

    private final String passwordHash;

    private final boolean active;

    private final String photo;

    private final LocalDateTime createdAt;

    public User(Long id, String name, String email, String passwordHash, boolean active, String photo, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.active = active;
        this.photo = photo;
        this.createdAt = createdAt;
    }

    public static User create(String name, String email, String passwordHash) {
        return new User(null, name, email, passwordHash, true, null, null);
    }

    public User withPhoto(String photo) {
        return new User(this.id, this.name, this.email, this.passwordHash, this.active, photo, this.createdAt);
    }
}
