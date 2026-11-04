package com.ctorres.pokequiz.infrastructure.persistence.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false)
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roleEntities = new HashSet<>();

    public UserEntity() {}

    public UserEntity(String username, String password, boolean active) {
        this.username = username;
        this.passwordHash = password;
        this.active = active;
    }

    public UserEntity(UUID id, String username, String password, boolean active, Set<RoleEntity> roles) {
        this.id = id;
        this.username = username;
        this.passwordHash = password;
        this.active = active;
        this.roleEntities = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<RoleEntity> getRoles() {
        return roleEntities;
    }

    public void setRoles(Set<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserEntity userEntity))
            return false;
        return id != null && id.equals(userEntity.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}