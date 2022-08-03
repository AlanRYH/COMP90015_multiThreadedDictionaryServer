/**
 * Author Name: RAO YUNHUI
 * Student Number:1316834
 */
package client;
import common.Message;
import org.json.JSONObject;//download json package from https://github.com/stleary/JSON-java

import java.io.*;
import java.net.*;


public class RequestSender extends Thread {
	DicClient client;
	private String ip;
	private int port_num;
	private String action;
	private String word;
	private String meaning;
	JSONObject resJSON;//store this for parent process to read, cannot call response_handler
					   //in child process as the UI JOptionPane.showMessageDialog will block the process and cause exception
	
	
	public RequestSender(DicClient client, String ip, int port_num, String action, String word, String meaning) {
		this.client = client;
		this.ip = ip;
		this.port_num = port_num;
		this.action = action;
		this.word = word;
		this.meaning = meaning;
	}
	
	public void run() {
		try {
			Socket server = new Socket(ip, port_num);
			DataInputStream is = new DataInputStream(server.getInputStream());
			DataOutputStream os = new DataOutputStream(server.getOutputStream());
			
			//sending request in the form of {"action":action, "word":word, "meaning":meaning}
			Message msg = new Message();
			msg.setAction(action);
			msg.setWord(word);
			msg.setMeaning(meaning);
			String msgJSON = new JSONObject(msg).toString();
			os.writeUTF(msgJSON);
			os.flush();
			
			//receiving response
			String response = new String(is.readUTF());
			resJSON = new JSONObject(response);
			is.close();
			os.close();
			server.close();
		} catch(UnknownHostException e) {
			client.print("Error: Unknown host!");
		} catch(IOException e) {
			client.print(("Error encountering when dealing with IO stream!"));
		} catch(Exception e) {
			client.print("Unknown exception!");
			e.printStackTrace();
		}
	}
}
