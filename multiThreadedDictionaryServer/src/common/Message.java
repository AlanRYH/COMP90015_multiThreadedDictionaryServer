/**
 * Author Name: RAO YUNHUI
 * Student Number:1316834
 */
package common;

/**
 * Message class is for constructing JSON sending from clients.
 *
 */
public class Message {
	private String action;
	private String word;
	private String meaning;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
}