/**
 * Author Name: RAO YUNHUI
 * Student Number:1316834
 */
package server;
import java.io.*;
import java.util.HashMap;

public class Dictionary {
	private String path;
	private HashMap<String, String> dictionary;
	private final String default_location = "dictionary.txt";
	public Dictionary(String path) {
		this.path = path;
		dictionary = new HashMap<String, String>();
		openDictionary();
	}
	
	@SuppressWarnings("unchecked")
	private void openDictionary() {
		try {
			File dic_file = new File(path);
			FileInputStream fileIn = new FileInputStream(dic_file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			dictionary = (HashMap<String, String>) in.readObject();
			if(dictionary == null) dictionary = new HashMap<String, String>();
			in.close();
			fileIn.close();
		} catch(FileNotFoundException e) {
			if(path.equals(default_location)) {
				System.out.println("Cannot find default file! Creating a new one!");
				createOrUpdateDictionary();
			}
			else {
				System.out.println("File does not exist! Opening default dictionary...");
				path = default_location;
				openDictionary();
			}
		} catch(IOException e) {
			System.out.println("Encountering error when opening default file!");
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			System.out.println("Object class not found for the file object!");
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createOrUpdateDictionary() {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(dictionary);
			out.close();
			fileOut.close();
		} catch(IOException e) {
			System.out.println("Encountering error when writing file!");
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized String query(String word) {
		return dictionary.get(word);
	}
	
	public synchronized boolean add(String word, String meaning) {
		if(dictionary.containsKey(word)) return false;
		
		dictionary.put(word, meaning);
		createOrUpdateDictionary();
		return true;
	}
	
	public synchronized boolean remove(String word) {
		if(!dictionary.containsKey(word)) return false;
		
		dictionary.remove(word);
		createOrUpdateDictionary();
		return true;
	}
	
	public synchronized boolean update(String word, String meaning) {
		if(!dictionary.containsKey(word)) return false;
		
		dictionary.remove(word);
		dictionary.put(word, meaning);
		createOrUpdateDictionary();
		return true;
	}
}
