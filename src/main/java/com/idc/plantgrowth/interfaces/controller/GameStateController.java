package com.idc.plantgrowth.interfaces.controller;

import com.idc.plantgrowth.application.command.CreateGameStateCommand;
import com.idc.plantgrowth.application.command.UpdateGameStateCommand;
import com.idc.plantgrowth.application.service.GameStateApplicationService;
import com.idc.plantgrowth.interfaces.dto.GameStateDto;
import com.idc.plantgrowth.interfaces.mapper.GameStateDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/game-state")
public class GameStateController {
    private final GameStateApplicationService service;
    private final GameStateDtoMapper gameStateDtoMapper;

    @PostMapping
    public GameStateDto create(@RequestBody CreateGameStateCommand cmd) {
        return gameStateDtoMapper.toDto(service.create(cmd));
    }

    @PutMapping
    public GameStateDto update(@RequestBody UpdateGameStateCommand cmd) {
        return gameStateDtoMapper.toDto(service.update(cmd));
    }

    @GetMapping("/{userId}/{gameId}")
    public GameStateDto get(
            @PathVariable UUID userId,
            @PathVariable UUID gameId
    ) {
        return gameStateDtoMapper.toDto(service.get(userId, gameId));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
