package exercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class Client {
	
	private String host;
	private int port;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@SuppressWarnings("resource")
	public void iniciar() throws IOException {
		System.out.print("Nome do Jogador: ");
		String nome = Console.readString();
		
		System.out.println("Conectando em " + host + ":" + port + "...");
		Socket socket = new Socket(host, port);
		System.out.println("Conexão estabelecida com sucesso!");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream out = new PrintStream(socket.getOutputStream());
		
		out.println(nome);
		
		System.out.println("Aguardando início da partida...");
		in.readLine();
		
		System.out.println("A partida foi iniciada!");
		
		while(true) {
			String msg = in.readLine();
			
			if(msg.equals("play")) {
				System.out.print("Efetue sua jogada: ");
				String jogada = Console.readString();
				System.out.println();
				
				out.println(jogada);
			
			} else if(msg.startsWith("error")) {
				System.out.println(msg.substring(6));
			
			} else if(msg.startsWith("matriz")) {
				String matriz = msg.substring(7);
				imprimirTabuleiro(matriz);
			
			} else if(msg.equals("draw")) {
				System.out.println("O jogo terminou empatado!");
				break;
			
			} else if(msg.startsWith("win")) {
				System.out.println("O jogador '" + msg.substring(4) + "' venceu a partida!");
				break;
			}
		}
	}
	
	private void imprimirTabuleiro(String matrizStr) {
		String[] tokens = matrizStr.split(",");
		
		char[][] matriz = new char[3][3];
		int i = 0;
		int j = 0;
		for (String token : tokens) {
			matriz[i][j] = token.charAt(0);
			j++;
			
			if(j > 2) {
				i++;
				j = 0;
			}
		}
		
		for (i = 0; i < matriz.length; i++) {
			for (j = 0; j < matriz[0].length; j++) {
				System.out.print(matriz[i][j]);
				if (j < matriz[0].length - 1) {
					System.out.print(" | ");
				}
			}
			System.out.println();

			if (i < matriz.length - 1) {
				System.out.println("---------");
			}
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws Exception {
		
		Client client = new Client("localhost", 1052);
		client.iniciar();
	}
}
