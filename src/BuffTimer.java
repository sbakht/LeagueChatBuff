import java.util.ArrayList;
import java.util.TimerTask;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class BuffTimer extends TimerTask {

	private ArrayList<Friend> timerFriends;
	private String name;
	private int timeLeft;
	
	public BuffTimer(ArrayList<Friend> timerFriends, String name, int timeLeft) {
		this.timerFriends = timerFriends;
		this.name = name;
		this.timeLeft = timeLeft;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(timeLeft <= 0) {
			for(Friend f : timerFriends) {
				if(name.contains("flash") || name.contains("ignite") || name.contains("exhaust") || name.contains("heal") || name.contains("ghost") ) {
					f.sendMessage(name + " is up");
				}else{
					f.sendMessage(name + " has spawned");
				}
			}
			this.cancel();
			return;
		}
		for(Friend f : timerFriends) {
			f.sendMessage(name + " spawn " + timeLeft/60 +  " minutes");
		}
		timeLeft -= 60;
	}
	
}