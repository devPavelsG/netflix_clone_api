package dev.pavelsgarklavs.netflix_clone.database.models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue
    @Setter
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @Setter
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @Setter
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}