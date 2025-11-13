package com.idc.plantgrowth.application.command;

import java.util.UUID;

public record CreateGameStateCommand(
        UUID userId,
        UUID gameId,
        String sceneId,
        String stateJson
) {
}
