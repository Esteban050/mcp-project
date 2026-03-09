package com.mcp.server.controller;

import com.mcp.server.dto.McpCallResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<McpCallResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(500)
                .body(McpCallResponse.failure("Error interno del servidor MCP: " + ex.getMessage()));
    }
}
