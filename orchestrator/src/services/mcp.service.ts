import axios from 'axios';

export interface McpTool {
  name: string;
  description: string;
  parameters: any;
}

export interface McpCallResponse {
  result: any;
  error: boolean;
  message?: string;
}

export class McpService {
  private readonly baseUrl: string;

  constructor() {
    this.baseUrl = process.env.MCP_SERVER_URL || 'http://localhost:8080';
  }

  async getTools(): Promise<McpTool[]> {
    try {
      const response = await axios.get<McpTool[]>(`${this.baseUrl}/mcp/tools`);
      return response.data;
    } catch (error: any) {
      console.error('Error al obtener herramientas del servidor MCP:', error.message);
      return [];
    }
  }

  async callTool(toolName: string, args: any): Promise<McpCallResponse> {
    try {
      const response = await axios.post<McpCallResponse>(`${this.baseUrl}/mcp/call`, {
        toolName,
        arguments: args
      });
      return response.data;
    } catch (error: any) {
      return {
        result: null,
        error: true,
        message: `Error al llamar a la herramienta ${toolName}: ${error.message}`
      };
    }
  }
}
