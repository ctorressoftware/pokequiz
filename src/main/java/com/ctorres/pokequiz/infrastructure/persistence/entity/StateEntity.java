package com.ctorres.pokequiz.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class StateEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false, length = 100)
    private String code;

    @Column(nullable = false)
    private boolean active;

    public StateEntity() {}

    public StateEntity(UUID id, String description, String code, boolean active) {
        this.id = id;
        this.description = description;
        this.code = code;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if (!(o instanceof StateEntity stateEntity))
            return false;
        return id != null && id.equals(stateEntity.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
