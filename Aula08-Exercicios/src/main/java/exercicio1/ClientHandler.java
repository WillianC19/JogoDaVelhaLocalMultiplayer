package exercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientHandler extends Thread {
	
	private static List<ClientHandler> clients = new ArrayList<ClientHandler>();

	private Socket clientSocket;
	
	private BufferedReader reader;
	
	private PrintStream writer;
	
	private String nick;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	public ClientHandler(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		
		reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		writer = new PrintStream(clientSocket.getOutputStream());
	}

	@Override
	public void run() {
		try {
			System.out.println("Cliente tentando se conectar...");

			String message = reader.readLine();

			if (message.startsWith("connect")) {
				nick = message.substring(message.indexOf(" ") + 1);

				writer.println("OK");

				System.out.println("Cliente " + nick + " conectado!");

				sendAll("[" + nick + " entrou no chat!]");

				addClient();

				while (true) {
					String text = reader.readLine();

					if (text.equalsIgnoreCase("[q]")) {
						break;
					}

					text = processMessage(text);
					
					sendAll(text);
				}
			}

		} catch (IOException e) {
			System.out.println("O cliente " + nick + "desconectou do chat");
		} finally {
			sendAll("[" + nick + " saiu do chat!]");
			removeClient();
			
			try {
				clientSocket.close();
			} catch (IOException e) {
			}
		}
	}

	private String processMessage(String message) {
		String time = sdf.format(new Date());

		return "[" + time + " " + nick + "] " + message;
	}

	public static void sendAll(String message) {
		synchronized (clients) {
			for (ClientHandler client : clients) {
				client.writer.println(message);
			}
		}
	}
	
	private void addClient() {
		synchronized (clients) {
			clients.add(this);
		}
	}
	
	private void removeClient() {
		synchronized (clients) {
			clients.remove(this);
		}
	}
}
