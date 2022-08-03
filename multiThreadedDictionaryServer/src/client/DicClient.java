/**
 * Author Name: RAO YUNHUI
 * Student Number:1316834
 */
package client;
import java.util.concurrent.TimeoutException;

import org.json.JSONObject;//download json package from https://github.com/stleary/JSON-java

/**
 * DicClient is the client-end of the dictionary system
 *
 */
public class DicClient {
	private String ip;
	private int port_num;
	private ClientGUI ui;
	
	public static void main(String[] args) {
		try {
			int port_num = Integer.parseInt(args[1]);
			if(port_num <=1024 || port_num >= 65536) {
				System.out.println("Port number out of range!(Valid port range: 1025~65535)");
				System.exit(-1);
			}
			System.out.println("Welcome to Dictionary!");
			DicClient client = new DicClient(args[0], port_num);
			client.run();
			
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("2 parameters needed!\n Please "
					+ "enter \"java ¨Cjar DictionaryClient.jar <server-address> <server-port>\" to run the client!");
		} catch(NumberFormatException e) {
			System.out.println("Invalid port number: port number should be a number between 1025 and 65535!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public DicClient(String ip, int port_num) {
		this.ip = ip;
		this.port_num = port_num;
	}
	
	public void run() {
		try {
			ui = new ClientGUI(this);
			ui.getFrame().setVisible(true);
		} catch(Exception e) {
			System.out.println("Error when opening the graphical user interface!");
			e.printStackTrace();
		}
	}
	
	//handle the response and show things get from server on GUI and command line
	public void response_handler(JSONObject response) {
		boolean state = response.getBoolean("state");
		String action = response.getString("action");
		String meaning = response.getString("meaning");
		String error_msg = response.getString("error_msg");
		
		if(state) {
			if(action.equals("QUERY")) {
				ui.set_meaning(meaning);
				System.out.println("Query operation processed!");
			}
			else {
				print("Operation successfully completed!");
			}
		}
		else {
			print(error_msg);
		}
	}
	
	//send request to server, will be called by ClientGUI object
	public void send_request(String action, String word, String meaning) {
		RequestSender sender = new RequestSender(this, ip, port_num, action, word, meaning);
		try{
			sender.start();
			
			sender.join(1000);
			response_handler(sender.resJSON);
			if(sender.isAlive()) {
				System.out.println(sender.getState());
				sender.interrupt();
				System.out.println(sender.getState());
				throw new TimeoutException();
			}
		} catch(InterruptedException e) {
			print("The message sent is interrupted!");
		} catch(TimeoutException e) {
			print("Time out for server's response!");
		} catch(Exception e) {
			print("Unknown exception!\n");
		}
		
	}
	
	//show things on GUI and command line
	public void print(String s) {
		System.out.println(s);
		ui.show_info(s);
	}
}
