package exercicio2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class Response {
	private int code;

	private String contentType;

	private File file;

	private PrintStream ps;
	private OutputStream os;

	private Response(OutputStream os) {
		this.os = os;
		ps = new PrintStream(os);
	}

	public static Response createResponse(OutputStream os) {
		return new Response(os);
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void send() throws HTTPServerException {
		try {
			System.out.println("Envio de resposta ao cliente...");
			
			ps.print("HTTP/1.0 " + code);

			System.out.println("Enviando código " + code + "...");

			if (code == 200) {
				this.contentType = Util.getContentType(file.getName());
				ps.print(" OK");
				ps.print("\r\n");
				ps.print("Content-Type: " + Util.getContentType(file.getName())
						+ "\r\n");
				System.out.println("Content-type '" + contentType + "'");
			} else if (code == 404) {
				ps.print("Not Found");
				ps.print("\r\n");
			}

			ps.print("Date: " + new Date() + "\r\n");
			ps.print("Server: " + Configuration.SERVER_NAME + "\r\n\r\n");

			if (file != null) {
				System.out.println("Enviando arquivo '" + file.getPath() + "'...");
				try (FileInputStream fis = new FileInputStream(file)) {
					byte[] buffer = new byte[1024];
					while (fis.available() > 0) {
						os.write(buffer, 0, fis.read(buffer));
					}

					os.flush();
				}
			}

			System.out.println("A resposta foi enviada ao cliente com sucesso!");

		} catch (IOException e) {
			throw new HTTPServerException(e);
		}
	}

	public int getCode() {
		return code;
	}

	public String getContentType() {
		return contentType;
	}

	public File getFile() {
		return file;
	}
}
