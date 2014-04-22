import java.util.TimerTask;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class BuffTimer extends TimerTask {

	private Friend friend;
	static private int timeLeft = 300;
	
	public BuffTimer(Friend friend) {
		this.friend = friend;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("In Buff Timer");
		friend.sendMessage("Blue Buff will spawn sometime in " + timeLeft +  " seconds");
		timeLeft -= 5;
		if(timeLeft == 0) {
			this.cancel();
		}
	}
	
}