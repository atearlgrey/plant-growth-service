package com.idc.plantgrowth.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateGameStateCommand {
    @NotNull(message = "{game_state.update.userId.required}")
    UUID userId;

    @NotNull(message = "{game_state.update.gameId.required}")
    UUID gameId;

    @NotBlank(message = "{game_state.update.stateJson.required}")
    String stateJson;

}
