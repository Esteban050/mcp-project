package com.mcp.server.controller;

import com.mcp.server.dto.McpCallRequest;
import com.mcp.server.dto.McpCallResponse;
import com.mcp.server.dto.McpTool;
import com.mcp.server.service.McpService;
import com.mcp.server.service.McpToolRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mcp")
@RequiredArgsConstructor
public class McpController {

    private final McpToolRegistry toolRegistry;
    private final McpService mcpService;

    @GetMapping("/tools")
    public List<McpTool> listTools() {
        return toolRegistry.getTools();
    }

    @PostMapping("/call")
    public McpCallResponse callTool(@RequestBody McpCallRequest request) {
        String toolName = request.getToolName();
        Map<String, Object> arguments = request.getArguments();

        switch (toolName) {
            case "buscarClientePorId":
                return mcpService.buscarClientePorId(getLongArgument(arguments, "id"));
            case "listarProductosDisponibles":
                return mcpService.listarProductosDisponibles((String) arguments.get("category"));
            case "consultarPedidosPorCliente":
                return mcpService.consultarPedidosPorCliente(getLongArgument(arguments, "clienteId"));
            default:
                return McpCallResponse.failure("La herramienta '" + toolName + "' no existe.");
        }
    }

    private Long getLongArgument(Map<String, Object> arguments, String key) {
        Object val = arguments.get(key);
        if (val instanceof Number) {
            return ((Number) val).longValue();
        }
        return null;
    }
}
