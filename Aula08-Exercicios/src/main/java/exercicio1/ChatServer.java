package exercicio1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer {

	@SuppressWarnings("resource")
	public void start(int port) throws IOException {
		
		ServerSocket serverSocket = new ServerSocket(port);
		
		System.out.println("Servidor de Chat aberto na porta " + port);
		System.out.println("Aguardando requisicoes...");
		
		while(true) {
			Socket clientSocket = serverSocket.accept();
		
			ClientHandler processor = new ClientHandler(clientSocket);
			processor.start();
		}
	}

	public static void main(String[] args) throws Exception {
		ChatServer server = new ChatServer();
		server.start(5000);
	}
}
