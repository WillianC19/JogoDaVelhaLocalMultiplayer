package exercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class Jogador {
	private String nome;
	
	private char simbolo;
	
	private BufferedReader in;
	
	private PrintStream out;
	
	public Jogador(String nome, char simbolo, BufferedReader in, PrintStream out) {
		this.nome = nome;
		this.simbolo = simbolo;
		this.in = in;
		this.out = out;
	}

	
	public String getNome() {
		return nome;
	}

	public char getSimbolo() {
		return simbolo;
	}
	
	public void closeConnection() throws IOException {
		in.close();
		out.close();
	}
	
	public Jogada obterJogada() throws IOException, JogadaInvalidaException {
		out.println("play");
		
		String jogada = in.readLine();

		return new Jogada(jogada);
	}

	public BufferedReader getIn() {
		return in;
	}

	public PrintStream getOut() {
		return out;
	}

	public void send(String msg) {
		out.println(msg);
	}
}
