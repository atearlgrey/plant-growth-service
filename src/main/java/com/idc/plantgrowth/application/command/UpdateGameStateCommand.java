package com.idc.plantgrowth.application.command;

import java.util.UUID;

public record UpdateGameStateCommand(
        UUID userId,
        UUID gameId,
        String newStateJson
) {
}
