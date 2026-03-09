package com.mcp.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpCallResponse {
    private Object result;
    private boolean error;
    private String message;

    public static McpCallResponse success(Object result) {
        return new McpCallResponse(result, false, null);
    }

    public static McpCallResponse failure(String message) {
        return new McpCallResponse(null, true, message);
    }
}
