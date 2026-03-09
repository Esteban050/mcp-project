package com.mcp.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class McpIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDiscovery_ShouldReturnThreeTools() throws Exception {
        mockMvc.perform(get("/mcp/tools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("buscarClientePorId")))
                .andExpect(jsonPath("$[1].name", is("listarProductosDisponibles")))
                .andExpect(jsonPath("$[2].name", is("consultarPedidosPorCliente")));
    }

    @Test
    public void testCall_BuscarCliente_ShouldReturnDataFromSql() throws Exception {
        String jsonRequest = "{\"toolName\": \"buscarClientePorId\", \"arguments\": {\"id\": 1}}";
        
        mockMvc.perform(post("/mcp/call")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is(false)))
                .andExpect(jsonPath("$.result.name", is("Juan Perez")))
                .andExpect(jsonPath("$.result.email", is("juan.perez@email.com")));
    }

    @Test
    public void testCall_ListarProductos_ShouldFilterOutOfStock() throws Exception {
        String jsonRequest = "{\"toolName\": \"listarProductosDisponibles\", \"arguments\": {}}";
        
        mockMvc.perform(post("/mcp/call")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is(false)))
                .andExpect(jsonPath("$.result", hasSize(4))) // De 5 productos en data.sql, uno tiene stock 0
                .andExpect(jsonPath("$.result[*].name", not(hasItem("Monitor 4K"))));
    }

    @Test
    public void testCall_ConsultarPedidos_ShouldReturnOrders() throws Exception {
        String jsonRequest = "{\"toolName\": \"consultarPedidosPorCliente\", \"arguments\": {\"clienteId\": 1}}";
        
        mockMvc.perform(post("/mcp/call")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is(false)))
                .andExpect(jsonPath("$.result", hasSize(2))); // Juan Perez tiene 2 pedidos en data.sql
    }
}
