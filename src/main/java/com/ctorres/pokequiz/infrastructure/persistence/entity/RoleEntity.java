package com.ctorres.pokequiz.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    public RoleEntity() {}

    public RoleEntity(UUID id, String name, String description, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RoleEntity roleEntity))
            return false;
        return id != null && id.equals(roleEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Role{id=" + id + ", name='" + name + "', active=" + active + '}';
    }
}
