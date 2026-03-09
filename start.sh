#!/bin/bash

# Iniciar Servidor MCP Java en segundo plano
echo "Iniciando Servidor MCP Java..."
cd mcp-server && mvn spring-boot:run &
JAVA_PID=$!

# Esperar a que el servidor Java inicie
echo "Esperando 10 segundos a que el servidor Java esté listo..."
sleep 10

# Iniciar Orquestador Node.js
echo "Iniciando Orquestador..."
cd ../orchestrator && npx tsx src/index.ts

# Al cerrar el orquestador, matar el servidor Java
kill $JAVA_PID
