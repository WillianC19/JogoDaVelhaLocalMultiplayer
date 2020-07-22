package exercicio2;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Worker implements Runnable {
	private Socket conn;
	
	public Worker(Socket conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		try {
			System.out.println("Thread iniciou atendimento o requisição");
			InputStream is = conn.getInputStream();
			OutputStream os = conn.getOutputStream();
			
			System.out.println("Fazendo parse da requisição...");
			Request request = Request.parseRequest(is);
			
			File file = request.getFile();
			System.out.println("Criando a resposta a ser enviada ao cliente...");
			Response response = Response.createResponse(os);
			
			if(!file.exists() || file.isDirectory()) {
				response.setCode(404);
			} else {		
				response.setCode(200);
				response.setFile(request.getFile());
			}
			
			response.send();
			
			conn.close();
			System.out.println("A conexão foi fechada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
