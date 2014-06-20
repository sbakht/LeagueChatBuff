import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class BuffTimer implements Runnable {

	private ArrayList<Friend> timerFriends;
	private String name;
	private int timeLeft;
	private String willSpawn;
	private String spawned;
	private Boolean isBuff;
	private ScheduledFuture<?> sF;
	 
	
	public BuffTimer(ArrayList<Friend> timerFriends, Objective objective, ScheduledFuture<?> sF) {
		this.timerFriends = timerFriends;
		this.name = objective.name;
		this.timeLeft = objective.timeLeft;
		this.isBuff = objective.isBuff;
		this.sF = sF;
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
		System.out.println("in buff timer");
		if(timeLeft <= 0) {
			for(Friend f : timerFriends) {
					f.sendMessage(name + spawned);
			}
			sF.cancel(true);
			return;
		}
		for(Friend f : timerFriends) {
		    DecimalFormat decimalFormat=new DecimalFormat("#.#");
			f.sendMessage(name + willSpawn + decimalFormat.format(timeLeft/60.0) +  " minutes");
			System.out.println("in send message");
		}
		timeLeft -= 60;
	}
	
}