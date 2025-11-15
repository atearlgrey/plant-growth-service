package com.idc.plantgrowth.interfaces.controller;

import com.idc.plantgrowth.application.command.CreateGameStateCommand;
import com.idc.plantgrowth.application.command.UpdateGameStateCommand;
import com.idc.plantgrowth.application.service.GameStateApplicationService;
import com.idc.plantgrowth.interfaces.common.ApiResponse;
import com.idc.plantgrowth.interfaces.common.ApiResponseFactory;
import com.idc.plantgrowth.interfaces.dto.GameStateDto;
import com.idc.plantgrowth.interfaces.mapper.GameStateDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/game-state")
@Tag(name = "Game State", description = "API quản lý lưu trữ trạng thái game cho từng user")
public class GameStateController {

    private final GameStateApplicationService service;
    private final GameStateDtoMapper gameStateDtoMapper;

    @Operation(
            summary = "Tạo mới game state",
            description = "Dùng khi user bắt đầu game lần đầu hoặc chưa có dữ liệu lưu.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Tạo thành công",
                            content = @Content(schema = @Schema(implementation = GameStateDto.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dữ liệu đầu vào không hợp lệ"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
            }
    )
    @PostMapping
    public ApiResponse<GameStateDto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Thông tin để tạo game state",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateGameStateCommand.class))
            )
            @RequestBody CreateGameStateCommand cmd
    ) {
        var result = service.create(cmd);
        return ApiResponseFactory.success(gameStateDtoMapper.toDto(result));
    }

    @Operation(
            summary = "Cập nhật game state",
            description = "Lưu vị trí hiện tại, setting, object trong game (đẩy khi user click Save hoặc tự động).",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Cập nhật thành công",
                            content = @Content(schema = @Schema(implementation = GameStateDto.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy game state"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
            }
    )
    @PutMapping
    public ApiResponse<GameStateDto> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Thông tin cập nhật state",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateGameStateCommand.class))
            )
            @RequestBody UpdateGameStateCommand cmd
    ) {
        var result = service.update(cmd);
        return ApiResponseFactory.success(gameStateDtoMapper.toDto(result));
    }

    @Operation(
            summary = "Lấy game state",
            description = "Lấy trạng thái game theo User ID và Game ID (ví dụ game: plant-growth).",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Lấy thành công",
                            content = @Content(schema = @Schema(implementation = GameStateDto.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
            }
    )
    @GetMapping("/{userId}/{gameId}")
    public ApiResponse<GameStateDto> get(
            @Parameter(description = "User ID cần lấy state", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Game ID", required = true)
            @PathVariable UUID gameId
    ) {
        var result = service.get(userId, gameId);
        return ApiResponseFactory.success(gameStateDtoMapper.toDto(result));
    }

    @Operation(
            summary = "Xoá game state",
            description = "Xoá trạng thái game theo ID (thường dùng cho admin hoặc reset dữ liệu).",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Xoá thành công"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Không tìm thấy"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
            }
    )
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @Parameter(description = "ID game state", required = true)
            @PathVariable UUID id
    ) {
        service.delete(id);
        return ApiResponseFactory.success(null);
    }
}
