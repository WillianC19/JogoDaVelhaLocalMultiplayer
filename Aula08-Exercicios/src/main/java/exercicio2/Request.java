package exercicio2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Request {
	private String type;
	
	private File file;
	
	private String protocolVersion;

	private Request(InputStream is) throws HTTPServerException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String request = reader.readLine();

			while (true) {
				String misc = reader.readLine();
				if (misc == null || misc.length() == 0) {
					break;
				}
			}

			String[] tokens = request.split(" ");

			String docRoot = Configuration.DOC_ROOT;

			this.type = tokens[0];
			this.file = new File(docRoot + "/" + tokens[1]);
			this.protocolVersion = tokens[2];

			System.out.println("Request recebida: " + request);
		} catch (IOException e) {
			throw new HTTPServerException(e);
		}
	}

	public static Request parseRequest(InputStream is) throws HTTPServerException {
		return new Request(is);
	}

	public String getType() {
		return type;
	}

	public File getFile() {
		return file;
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

}
