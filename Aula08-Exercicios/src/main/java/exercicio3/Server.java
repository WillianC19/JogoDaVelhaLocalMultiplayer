package exercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {

	private int port;
	private List<Jogador> jogadores = new ArrayList<Jogador>();
	private Tabuleiro tabuleiro = new Tabuleiro();
	
	public Server(int port) {
		this.port = port;
	}
	
	public void iniciar() throws IOException {
		
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Servidor aberto na porta " + port + ". Aguardando os jogadores...");
		
		int numJogadores = 0;
		char simbolo = 'X';
		
		while(numJogadores < 2) {
			Socket clientSocket = serverSocket.accept();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream out = new PrintStream(clientSocket.getOutputStream());
			
			String nome = in.readLine();
			
			System.out.println("Jogador '" + nome + "' conectado!");
			jogadores.add(new Jogador(nome, simbolo, in, out));
			
			simbolo = 'O';
			numJogadores++;
		}
		
		sendAll("start");
		
		boolean finalizado = false;
		int indexJogadorAtual = 0;
		Jogador vencedor = null;

		while (!finalizado) {
			String matriz = tabuleiro.getMatrizAsString();
			sendAll("matriz " + matriz);
			
			Jogador jogador = jogadores.get(indexJogadorAtual);

			boolean sequenciaEncontrada = false;
			boolean jogadaValida = false;
			
			while(!jogadaValida) {
				try {
					Jogada jogada = jogador.obterJogada();
					sequenciaEncontrada = tabuleiro.efetuarJogada(jogada, jogador.getSimbolo());
					jogadaValida = true;
				} catch (JogadaInvalidaException e) {
					jogador.send("error " + e.getMessage());
				}
			}

			if (sequenciaEncontrada) {
				vencedor = jogador;
				finalizado = true;
			} else if (tabuleiro.isCompleto()) {
				finalizado = true;
			}

			indexJogadorAtual = (indexJogadorAtual + 1) % jogadores.size();
		}
		
		if (vencedor == null) {
			sendAll("draw");
		} else {
			sendAll("win " + vencedor.getNome());
		}
		
		System.out.println("A partida terminou!");
		
		for(Jogador jogador : jogadores) {
			jogador.closeConnection();
		}
		
		serverSocket.close();
	}
	
	private void sendAll(String msg) {
		jogadores.stream().forEach(e -> e.send(msg));
	}
	
	public static void main(String[] args) throws Exception {
		//alterado essa parte
		new Server(1052).iniciar();;
	}
}
