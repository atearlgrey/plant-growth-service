package com.idc.plantgrowth.domain.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class GameState {
    private UUID id;
    private UUID userId;
    private UUID gameId;
    private String sceneId;
    private String state; // JSON string
    private LocalDateTime updatedAt;

    public GameState(UUID id, UUID userId, UUID gameId, String sceneId, String state) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
        this.sceneId = sceneId;
        this.state = state;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateState(String newState) {
        this.state = newState;
        this.updatedAt = LocalDateTime.now();
    }
}
