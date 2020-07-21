package exercicio3;

public class Tabuleiro {

	private char[][] matriz;

	public Tabuleiro() {
		matriz = new char[3][3];
		
		zerar();
	}
	
	public void zerar() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				matriz[i][j] = ' ';
			}
		}
	}
	
	public String getMatrizAsString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				if(!first) {
					sb.append(",");
				}
				sb.append(matriz[i][j]);
				first = false;
			}
		}
		
		return sb.toString();
	}

	public boolean isCompleto() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				if (matriz[i][j] == ' ') {
					return false;
				}
			}
		}

		return true;
	}

	public boolean efetuarJogada(Jogada jogada, char simbolo) throws JogadaInvalidaException {
		int i = jogada.getI();
		int j = jogada.getJ();

		if (i < 0 || j < 0 || i >= matriz.length || j >= matriz[0].length) {
			throw new JogadaInvalidaException("A jogada " + i + ", " + j + " e invalida");
		}

		if (matriz[i][j] != ' ') {
			throw new JogadaInvalidaException("A jogada " + i + ", " + j + " ja foi realizada");
		}

		matriz[i][j] = simbolo;

		return isSequenciaEncontrada();
	}
	
	private boolean isSequenciaEncontrada() {
		if(matriz[0][0] == matriz[0][1] && matriz[0][1] == matriz[0][2] && matriz[0][0] != ' ') {
			return true;
		}
		
		if(matriz[1][0] == matriz[1][1] && matriz[1][1] == matriz[1][2] && matriz[1][0] != ' ') {
			return true;
		}
		
		if(matriz[2][0] == matriz[2][1] && matriz[2][1] == matriz[2][2] && matriz[2][0] != ' ') {
			return true;
		}
		
		if(matriz[0][0] == matriz[1][0] && matriz[1][0] == matriz[2][0] && matriz[0][0] != ' ') {
			return true;
		}
		
		if(matriz[0][1] == matriz[1][1] && matriz[1][1] == matriz[2][1] && matriz[0][1] != ' ') {
			return true;
		}
	
		if(matriz[0][2] == matriz[1][2] && matriz[1][2] == matriz[2][2] && matriz[0][2] != ' ') {
			return true;
		}
		
		if(matriz[0][0] == matriz[1][1] && matriz[1][1] == matriz[2][2] && matriz[0][0] != ' ') {
			return true;
		}
		
		if(matriz[0][2] == matriz[1][1] && matriz[1][1] == matriz[2][0] && matriz[0][2] != ' ') {
			return true;
		}
	
		return false;
	}
}
