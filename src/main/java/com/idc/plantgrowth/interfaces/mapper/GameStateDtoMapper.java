package com.idc.plantgrowth.interfaces.mapper;

import com.idc.plantgrowth.domain.model.entity.GameState;
import com.idc.plantgrowth.interfaces.dto.GameStateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameStateDtoMapper {
    GameStateDto toDto(GameState gameState);
}
