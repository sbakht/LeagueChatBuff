import com.github.theholywaffle.lolchatapi.LolChat;


public class Account {
	
	private Boolean isLoggedIn = false;
	
	public void login(String username, String password, LolChat api) {
	    try {
	    	if(!api.login(username, password)) {
	    		return;
	    	}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    isLoggedIn = true;	
	}
	
	public Boolean isLoggedIn() {
		return this.isLoggedIn;
	}
	
}
