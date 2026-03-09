import * as dotenv from 'dotenv';
import path from 'path';
import * as readline from 'readline';
import { McpService } from './services/mcp.service';
import { GeminiService } from './services/gemini.service';

// Cargar variables de entorno desde la raíz
dotenv.config({ path: path.join(__dirname, '../../.env') });

const apiKey = process.env.GEMINI_API_KEY;

if (!apiKey) {
  console.error('Error: GEMINI_API_KEY no definida en el entorno.');
  process.exit(1);
}

const mcpService = new McpService();
const geminiService = new GeminiService(apiKey, mcpService);

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
  prompt: 'Tú > '
});

async function start() {
  console.log('\n--- Agente Inteligente MCP iniciado ---');
  console.log('Escribe "salir" o presiona Ctrl+C para terminar.');
  
  try {
    await geminiService.init();
    console.log('----------------------------------------\n');
    
    rl.prompt();

    rl.on('line', async (line) => {
      const input = line.trim();
      
      if (input.toLowerCase() === 'salir' || input.toLowerCase() === 'exit') {
        rl.close();
        return;
      }

      if (!input) {
        rl.prompt();
        return;
      }

      process.stdout.write('Agente > Pensando...\r');
      
      try {
        const response = await geminiService.chatMessage(input);
        console.log('Agente > ' + response);
      } catch (error: any) {
        console.error('\nError en el agente:', error.message);
      }

      console.log('');
      rl.prompt();
    }).on('close', () => {
      console.log('\n¡Hasta pronto!');
      process.exit(0);
    });

  } catch (error: any) {
    console.error('Error fatal durante el inicio:', error.message);
    process.exit(1);
  }
}

start();
