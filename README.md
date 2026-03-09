# Agente Inteligente con MCP (Java) y Orquestador (Node.js)

Este proyecto es una implementación de referencia de un **Agente Inteligente** que utiliza la API de **Google Gemini** como motor de lenguaje, orquestado por un backend en **Node.js**, y capaz de consultar datos en tiempo real mediante el protocolo **MCP (Model Context Protocol)** desde un servidor implementado en **Java**.

## 🚀 Descripción

El objetivo es demostrar la interoperabilidad entre diferentes ecosistemas (Node.js y Java) utilizando el protocolo MCP para permitir que un LLM moderno tome decisiones autónomas sobre cuándo y cómo consultar sistemas de datos empresariales.

## 🏗️ Arquitectura del Sistema

El sistema sigue una arquitectura de tres capas desacopladas:

```text
[ Cliente (CLI/REST) ]
        │
        ▼
[ Orquestador Node.js ] <───> [ Google Gemini API ]
        │
        ▼
[ Servidor MCP Java ]   <───> [ Base de Datos (H2/JPA) ]
```

1.  **Orquestador (Node.js/TS):** Gestiona el flujo de la conversación, el contexto del prompt y el ciclo de *tool-calling*.
2.  **Servidor MCP (Java/Spring Boot):** Expone herramientas (tools) estandarizadas para que el agente pueda realizar consultas a la base de datos.
3.  **Protocolo MCP:** Comunicación basada en JSON sobre HTTP para el descubrimiento y ejecución de herramientas.

## 🛠️ Stack Tecnológico

| Componente | Tecnología |
|---|---|
| **LLM** | Google Gemini 1.5 Flash |
| **Orquestador** | Node.js 20+, TypeScript, Dotenv, Axios |
| **Servidor MCP** | Java 17+, Spring Boot 3.2, Maven, Lombok |
| **Base de Datos** | H2 (En memoria, inicializada dinámicamente) |
| **Protocolo** | Model Context Protocol (MCP) |

## 📦 Estructura del Proyecto

```text
mcp-project/
├── orchestrator/    # Proyecto Node.js (TypeScript)
├── mcp-server/      # Proyecto Java (Spring Boot + Maven)
├── start.ps1        # Script de arranque para Windows
├── start.sh         # Script de arranque para Linux/Mac
├── .env.example     # Plantilla de variables de entorno
└── README.md        # Documentación principal
```

## ⚙️ Configuración e Instalación

### Requisitos Previos
- Node.js v20.x o superior
- Java JDK 17 o superior
- Maven 3.8+
- Una API Key de [Google AI Studio (Gemini)](https://aistudio.google.com/)

### Paso 1: Configurar entorno
1. Clonar el repositorio.
2. Crear un archivo `.env` en la raíz basado en `.env.example`:
   ```bash
   cp .env.example .env
   ```
3. Editar `.env` y añadir tu `GEMINI_API_KEY`.

### Paso 2: Ejecución Rápida
He incluido scripts para automatizar el arranque de ambos servicios (Java y Node.js) simultáneamente:

- **En Windows:**
  ```powershell
  .\start.ps1
  ```
- **En Linux/Mac:**
  ```bash
  chmod +x start.sh
  ./start.sh
  ```

*Nota: El script de arranque espera 10 segundos a que el servidor Java esté listo antes de lanzar el orquestador.*

## 🛠️ Herramientas Disponibles (MCP Tools)
El servidor Java expone dinámicamente las siguientes capacidades a Gemini:
- `buscarClientePorId`: Retorna información básica de un cliente.
- `listarProductosDisponibles`: Filtra productos con stock disponible.
- `consultarPedidosPorCliente`: Historial de pedidos recientes de un cliente.

## 📝 Licencia
Este proyecto es de código abierto bajo la licencia MIT.
