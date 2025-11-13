package com.idc.plantgrowth.interfaces.dto;

import java.util.UUID;

public record GameStateDto(
        UUID id,
        UUID userId,
        UUID gameId,
        String sceneId,
        String state
) {
}
