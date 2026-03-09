import { GoogleGenerativeAI, GenerativeModel, ChatSession, FunctionDeclaration } from '@google/generative-ai';
import { McpTool, McpService } from './mcp.service';

export class GeminiService {
  private genAI: GoogleGenerativeAI;
  private model!: GenerativeModel;
  private chatSession!: ChatSession;
  private mcpService: McpService;

  constructor(apiKey: string, mcpService: McpService) {
    this.genAI = new GoogleGenerativeAI(apiKey);
    this.mcpService = mcpService;
  }

  async init() {
    const mcpTools = await this.mcpService.getTools();
    const functionDeclarations = this.mapMcpToolsToGemini(mcpTools);

    try {
      this.model = this.genAI.getGenerativeModel({
        model: 'gemini-2.5-flash',
        tools: [{ functionDeclarations }],
      });

      this.chatSession = this.model.startChat({
        history: [],
      });

      console.log(`Gemini inicializado con ${functionDeclarations.length} herramientas.`);
    } catch (error: any) {
      console.error('Error al inicializar Gemini:', error.message);
      throw error;
    }
  }

  private mapMcpToolsToGemini(mcpTools: McpTool[]): FunctionDeclaration[] {
    return mcpTools.map(tool => ({
      name: tool.name,
      description: tool.description,
      parameters: tool.parameters
    }));
  }

  getChatSession(): ChatSession {
    return this.chatSession;
  }

  async chatMessage(userInput: string): Promise<string> {
    let result = await this.chatSession.sendMessage(userInput);
    let response = result.response;

    // Ciclo de Tool-Calling
    while (response.functionCalls()?.length) {
      const functionCalls = response.functionCalls()!;
      const parts = [];

      for (const call of functionCalls) {
        console.log(`[MCP] Ejecutando tool: ${call.name} con argumentos:`, call.args);
        const mcpResponse = await this.mcpService.callTool(call.name, call.args);

        // Formatear la respuesta como Gemini espera (objeto plano bajo la llave 'response')
        const toolResult = mcpResponse.error ? { error: mcpResponse.message } : mcpResponse.result;

        parts.push({
          functionResponse: {
            name: call.name,
            response: { content: toolResult } // Envolvemos en 'content' para mayor compatibilidad
          }
        });
      }

      // Enviar resultados de vuelta a Gemini
      result = await this.chatSession.sendMessage(parts);
      response = result.response;
    }

    return response.text();
  }
}
