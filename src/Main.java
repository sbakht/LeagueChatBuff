
import java.util.ArrayList;
import java.util.Timer;

import com.github.theholywaffle.lolchatapi.ChatServer;
import com.github.theholywaffle.lolchatapi.LolChat;
import com.github.theholywaffle.lolchatapi.listeners.ChatListener;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class Main {
	
	static private boolean discon;
	
	public static void main(String args[]) {

		LolChat api = new LolChat(ChatServer.NA, true);
		
		if (api.login("sealsmurf1", "sealsmurf2")) {

		    try {
		        Thread.sleep(1000); // Give server some time to send us all the data
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    
			final Friend saad = api.getFriendByName("iwanthotdogs");
			final Friend vishal = api.getFriendByName("sealiest seal");
			final ArrayList<Friend> timerFriends = new ArrayList<>();
			timerFriends.add(saad);
			timerFriends.add(vishal);
		    
		    
		    //Example 2: Adding ChatListener - listens to chat messages from any of your friends.

		    api.addChatListener(new ChatListener() {
				
				public void onMessage(Friend friend, String message) {
					
					message = (String) message.trim();
					System.out.println("[All]>" + friend.getName() + ": " + message);
					
					Timer timer = new Timer();
					switch (message) {

						case "ob":
		            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"BLUE", 300,true), 0, 60*1000);
							break;
						case "or":
		            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"RED", 300,true), 0, 60*1000);
							break;
						case "tb":
		            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"ENEMEY BLUE", 300,true), 0, 60*1000);
							break;
						case "tr":
		            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"ENEMY RED", 300,true), 0, 60*1000);
							break;
						case "drag":
							timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"DRAGON", 360,true), 0, 60*1000);
							break;
						case "baron":
							timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"BARON", 420,true), 0, 60*1000);
							break;
					}
					
					if(message.toLowerCase().contains("flash")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends,message, 300,false), 0, 60*1000);
					}
					
					if(message.toLowerCase().contains("ignite") || message.contains("exhaust") || message.contains("ghost")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends,message, 210,false), 0, 60*1000);
					}
					
					if(message.toLowerCase().contains("heal")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends,message, 240,false), 0, 60*1000);
					}
					
					if(message.equals("disconnect")) {
						discon = true;
					}
					System.out.println(discon);

				}
			});
		  
//		    api.disconnect();
		    
		}
	}

}
