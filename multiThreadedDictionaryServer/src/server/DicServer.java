/**
 * Author Name: RAO YUNHUI
 * Student Number:1316834
 */
package server;

import java.net.*;

/**
 * DicServer is the server-end of the class
 *
 */
public class DicServer {
	private int port;
	private ServerSocket server;
	private Dictionary dictionary;
	
	public static void main(String[] args) {
		//args are <port> and <dictionary-file>
		try {
			int port_num = Integer.parseInt(args[0]);
			if(port_num <=1024 || port_num >= 65536) {
				System.out.println("Port number out of range!(Valid port range: 1025~65535)");
				System.exit(-1);
			}
			DicServer dicserver = new DicServer(port_num, args[1]);
			dicserver.run();
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("2 parameters needed!\n Please "
					+ "enter \"java ¨Cjar DictionaryServer.jar <port> <dictionary-file>\" to run the server!");
		} catch(NumberFormatException e) {
			System.out.println("Invalid port number: port number should be a number between 1025 and 65535!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public DicServer(int port_num, String dic_path) {
		port = port_num;
		dictionary = new Dictionary(dic_path);
	}
	
	public void run() {
		try {
			server = new ServerSocket(port);
			printStatus();
			while(true) {
				Socket client = server.accept();
				RequestHandler handler = new RequestHandler(client, dictionary);
				handler.start();
			}
		} catch(BindException e) {
			System.out.println("Port is not valid for use, try another!");
		} catch(UnknownHostException e) {
			System.out.println("Unknown host!");
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void printStatus() throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();
		System.out.println("Server running...");
		System.out.println("IP address: " + ip.getHostAddress());
		System.out.println("Port number: " + port);
		System.out.println("Waiting for client connection...");
	}
	
}
