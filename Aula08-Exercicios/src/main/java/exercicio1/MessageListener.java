package exercicio1;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageListener extends Thread {

	private BufferedReader reader;
	
	private boolean stop;

	public MessageListener(BufferedReader reader) {
		this.reader = reader;
	}

	@Override
	public void run() {
		try {
			while (!stop) {
				String text = reader.readLine();
				if (text != null) {
					System.out.println(text);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
