import java.util.ArrayList;
import java.util.TimerTask;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class BuffTimer extends TimerTask {

	private ArrayList<Friend> timerFriends;
	private String name;
	private int timeLeft;
	private String willSpawn;
	private String spawned;
	private Boolean isBuff;
	 
	
	public BuffTimer(ArrayList<Friend> timerFriends, Objective objective) {
		this.timerFriends = timerFriends;
		this.name = objective.name;
		this.timeLeft = objective.timeLeft;
		this.isBuff = objective.isBuff;
		spawnStrings();
	}
	
	public void spawnStrings() {
		if(isBuff) {
			willSpawn = " spawn ";
			spawned = " has spawned";
		}else{
			willSpawn = " back in ";
			spawned = " is up";
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(timeLeft <= 0) {
			for(Friend f : timerFriends) {
					f.sendMessage(name + spawned);
			}
			this.cancel();
			return;
		}
		for(Friend f : timerFriends) {
			f.sendMessage(name + willSpawn + timeLeft/60 +  " minutes");
		}
		timeLeft -= 60;
	}
	
}