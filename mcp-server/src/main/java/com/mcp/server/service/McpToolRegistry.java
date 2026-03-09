package com.mcp.server.service;

import com.mcp.server.dto.McpTool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class McpToolRegistry {

    private final List<McpTool> tools = new ArrayList<>();

    public McpToolRegistry() {
        // Herramienta 1: buscarClientePorId
        tools.add(new McpTool(
            "buscarClientePorId",
            "Busca un cliente por su ID único y retorna su nombre, email y fecha de registro.",
            Map.of(
                "type", "object",
                "properties", Map.of(
                    "id", Map.of("type", "number", "description", "ID del cliente a buscar")
                ),
                "required", List.of("id")
            )
        ));

        // Herramienta 2: listarProductosDisponibles
        tools.add(new McpTool(
            "listarProductosDisponibles",
            "Retorna una lista de productos con stock disponible, opcionalmente filtrados por categoría.",
            Map.of(
                "type", "object",
                "properties", Map.of(
                    "category", Map.of("type", "string", "description", "Categoría opcional para filtrar")
                )
            )
        ));

        // Herramienta 3: consultarPedidosPorCliente
        tools.add(new McpTool(
            "consultarPedidosPorCliente",
            "Retorna el historial de pedidos de un cliente específico.",
            Map.of(
                "type", "object",
                "properties", Map.of(
                    "clienteId", Map.of("type", "number", "description", "ID del cliente para consultar sus pedidos")
                ),
                "required", List.of("clienteId")
            )
        ));
    }

    public List<McpTool> getTools() {
        return tools;
    }
}
