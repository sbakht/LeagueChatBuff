import java.util.TimerTask;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class BuffTimer extends TimerTask {

	private Friend friend;
	
	public BuffTimer(Friend friend) {
		this.friend = friend;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("In Buff Timer");
		friend.sendMessage("Blue Buff will spawn sometime in the future");
	}
	
}