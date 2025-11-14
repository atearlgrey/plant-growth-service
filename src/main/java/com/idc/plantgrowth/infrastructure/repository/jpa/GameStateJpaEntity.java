package com.idc.plantgrowth.infrastructure.repository.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(
        name = "game_state",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "game_id"})
)
@Getter
@Setter
public class GameStateJpaEntity {
    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @Column(name = "scene_id", length = 100)
    private String sceneId;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String state;

    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;
}
