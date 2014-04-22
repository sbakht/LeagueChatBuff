import java.util.TimerTask;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class BuffTimer extends TimerTask {

	private Friend friend;
	private String name;
//	static private int timeLeft = 300;
	private int timeLeft;
	
	public BuffTimer(Friend friend, String name, int timeLeft) {
		this.friend = friend;
		this.name = name;
		this.timeLeft = timeLeft;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("In " + name + " Timer");
		friend.sendMessage(name + " will spawn sometime in " + timeLeft +  " seconds");
		timeLeft -= 5;
		if(timeLeft == 0) {
			friend.sendMessage(name + " has respawned");
			this.cancel();
		}
	}
	
}