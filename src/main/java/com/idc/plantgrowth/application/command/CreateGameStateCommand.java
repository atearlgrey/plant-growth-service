package com.idc.plantgrowth.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateGameStateCommand {
    @NotNull(message = "{game_state.create.userId.required}")
    UUID userId;

    @NotNull(message = "{game_state.create.gameId.required}")
    UUID gameId;

    String sceneId;

    @NotBlank(message = "{game_state.create.stateJson.required}")
    String stateJson;

}
