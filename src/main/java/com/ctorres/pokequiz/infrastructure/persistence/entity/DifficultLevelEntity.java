package com.ctorres.pokequiz.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class DifficultLevelEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private boolean active;

    public DifficultLevelEntity() {
    }

    public DifficultLevelEntity(UUID id, String description, boolean active) {
        this.id = id;
        this.description = description;
        this.active = active;
    }

    public DifficultLevelEntity(String description, boolean active) {
        this.description = description;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "DifficultLevel{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DifficultLevelEntity))
            return false;
        DifficultLevelEntity that = (DifficultLevelEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
