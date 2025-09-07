package com.ctorres.pokequiz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private boolean active;

    // Constructor vacío (requerido por JPA)
    public User() {
    }

    // Constructor con todos los campos
    public User(Long id, String username, String password, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    // Constructor sin id (útil para crear nuevas entidades)
    public User(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // toString()
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", active=" + active +
                '}';
    }

    // equals() y hashCode() (solo usan id para evitar problemas en JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

}
