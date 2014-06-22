import java.util.TimerTask;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class MinimapTimer extends TimerTask {
	private Friend friend;
	
	public MinimapTimer(Friend friend) {
		this.friend = friend;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		friend.sendMessage("Look at the MINIMAP");
	}

}
