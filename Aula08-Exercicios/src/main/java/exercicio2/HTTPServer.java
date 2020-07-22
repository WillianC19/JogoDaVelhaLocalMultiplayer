package exercicio2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
	
	@SuppressWarnings("resource")
	public void start() throws HTTPServerException {
		System.out.println("Iniciando servidor...");
		try {
			ServerSocket socket = new ServerSocket(Configuration.PORT);
			
			System.out.println("Servidor aberto na porta " + Configuration.PORT);
			
			while(true) {
				System.out.println("Aguardando requisições...");
				
				Socket conn = socket.accept();
				System.out.println("Conexão estabelecida. Criando thread para atender a requisição do cliente...");
				
				Worker w = new Worker(conn);
				new Thread(w).start();
			}
		} catch (IOException e) {
			throw new HTTPServerException(e);
		}
	}
}
