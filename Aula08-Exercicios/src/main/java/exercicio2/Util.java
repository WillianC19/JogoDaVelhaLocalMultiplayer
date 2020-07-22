package exercicio2;

public class Util {

	public static String getContentType(String fileName) {
		if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
			return "text/html";
		} else if (fileName.endsWith(".gif")) {
			return "image/gif";
		} else if (fileName.endsWith(".png")) {
			return "image/x-png";
		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		} else {
			return "text/plain";
		}
	}
}
