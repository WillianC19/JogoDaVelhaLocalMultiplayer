package exercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatClient {

	@SuppressWarnings("resource")
	public void start(String server, int port) throws IOException {
		
		System.out.print("Digite o seu apelido: ");
		String nick = Console.readString();
		
		System.out.println("Conectando no servidor " + server + ":" + port + "...");
		Socket socket = new Socket(server, port);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream writer = new PrintStream(socket.getOutputStream());
		
		writer.println("connect " + nick);
		
		String response = reader.readLine();
		
		if(response.equals("OK")) {
			System.out.println("O servidor autorizou a conexao. Cliente conectado!");
			
			MessageListener listener = new MessageListener(reader);
			listener.start();
			
			while(true) {
				String message = Console.readString();
				
				writer.println(message);
				
				if(message.equalsIgnoreCase("[q]")) {
					listener.setStop(true);
					break;
				}
			}
		} else {
			System.out.println("O servidor respondeu: " + response + ". O cliente nao pode continuar");
		}
	}

	public static void main(String[] args) throws Exception {
		ChatClient server = new ChatClient();
		
		server.start("localhost", 5000);
	}
}
