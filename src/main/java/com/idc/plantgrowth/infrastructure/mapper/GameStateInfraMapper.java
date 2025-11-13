package com.idc.plantgrowth.infrastructure.mapper;

import com.idc.plantgrowth.domain.model.entity.GameState;
import com.idc.plantgrowth.infrastructure.repository.jpa.GameStateJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameStateInfraMapper {

    GameState toDomain(GameStateJpaEntity entity);

    @Mapping(target = "updatedAt", expression = "java(state.getUpdatedAt())")
    GameStateJpaEntity toJpa(GameState state);
}