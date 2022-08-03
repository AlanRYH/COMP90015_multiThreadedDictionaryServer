/**
 * Author Name: RAO YUNHUI
 * Student Number:1316834
 */
package server;

import java.net.*;
import java.io.*;
import org.json.JSONObject;//download json package from https://github.com/stleary/JSON-java
import common.Response;

/**
 * RequestHandler is a thread called by DicServer to accomplish thread-per-request mode
 * RequestHandler receives JSON string in the form of {"action":action, "word":word, meaning:"meaning"}(check message.java)
 * and responds JSON string in the form of{"state":state, "meaning":meaning, "error_msg":error_msg}(check Response.java)
 */
public class RequestHandler extends Thread{
	private Socket client;
	private Dictionary dictionary;
	
	public RequestHandler(Socket client, Dictionary dictionary) {
		this.client = client;
		this.dictionary = dictionary;
	}
	
	public void run() {
		try {
			DataInputStream is = new DataInputStream(client.getInputStream());
			DataOutputStream os = new DataOutputStream(client.getOutputStream());
			String s = new String(is.readUTF());
			JSONObject request = new JSONObject(s);
			String action = request.getString("action");
			String word = request.getString("word");
			String meaning = request.getString("meaning");
			
			//response with {"state":state, "action":action, "meaning":meaning, "error_msg":error_msg}
			if(action.equals("QUERY")) {
				String meaning_query = dictionary.query(word);
				Response response = new Response();
				response.setAction(action);
				if(meaning_query!=null) {
					response.setMeaning(meaning_query);
					response.setState(true);
				}
				else {
					response.setState(false);
					response.setError_msg("Word does not exist!");
				}
				String resJSON = new JSONObject(response).toString();
				os.writeUTF(resJSON);
				os.flush();
				System.out.println(word + " queried.");
			}
			//response with {"state":state, "action":action, "meaning":"", "error_msg":error_msg}
			else if(action.equals("ADD")) {
				Response response = new Response();
				response.setAction(action);
				if(meaning.equals("")) {
					response.setState(false);
					response.setError_msg("Meaning cannot be empty!");
				}
				else {
					boolean state = dictionary.add(word, meaning);
					response.setState(state);
					if(!state) response.setError_msg("Word already exists!");
				}
				String resJSON = new JSONObject(response).toString();
				os.writeUTF(resJSON);
				os.flush();
				System.out.println("The meaning for word \"" + word + "\" has been added.");
			}
			//response with {"state":state, "action":action, "meaning":"", "error_msg":error_msg}
			else if(action.equals("REMOVE")) {
				Response response = new Response();
				response.setAction(action);
				boolean state = dictionary.remove(word);
				response.setState(state);
				if(!state) {
					response.setError_msg("Word does not exist!");
				}
				else {
					System.out.println("The  word \"" + word + "\" has been removed.");
				}
				String resJSON = new JSONObject(response).toString();
				os.writeUTF(resJSON);
				os.flush();
			}
			//response with {"state":state, "action":action, "meaning":"", "error_msg":error_msg}
			else if(action.equals("UPDATE")) {
				Response response = new Response();
				response.setAction(action);
				if(meaning.equals("")) {
					response.setState(false);
					response.setError_msg("Meaning cannot be empty!");
				}
				else {
					boolean state = dictionary.update(word, meaning);
					response.setState(state);
					if(!state) response.setError_msg("Word does not exist!");
				}
				String resJSON = new JSONObject(response).toString();
				os.writeUTF(resJSON);
				os.flush();
				System.out.println("The meaning for word \"" + word + "\" has been updated.");
			}
			//response with {"state":false, "action":action, "meaning":"", "error_msg":error_msg}
			else {
				Response response = new Response();
				response.setAction(action);
				response.setState(false);
				response.setError_msg("Request cannot be resolved!");
				String resJSON = new JSONObject(response).toString();
				os.writeUTF(resJSON);
				os.flush();
				System.out.println("Action cannot be resolved!");
			}
			
			is.close();
			os.close();
			client.close();
		} catch(IOException e){
			System.out.println("Encountering error when parsing streams!");
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
