# Iniciar Servidor MCP Java en una nueva ventana
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd mcp-server; mvn spring-boot:run"

# Esperar a que el servidor Java inicie (aprox 10s)
Write-Host "Iniciando Servidor MCP Java... Esperando 10 segundos..."
Start-Sleep -Seconds 10

# Iniciar Orquestador Node.js
Write-Host "Iniciando Orquestador..."
cd orchestrator
npx tsx src/index.ts
