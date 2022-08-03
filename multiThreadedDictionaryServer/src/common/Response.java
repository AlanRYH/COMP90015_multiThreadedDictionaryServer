/**
 * Author Name: RAO YUNHUI
 * Student Number:1316834
 */
package common;

/**
 * Response class is for constructing JSON sending from Server.
 *
 */
public class Response {
	private boolean state;
	private String action;
	private String meaning;
	private String error_msg;
	
	public Response() {
		state = false;
		action = "";
		meaning = "";
		error_msg = "";
	}
	
	public String isAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
}
