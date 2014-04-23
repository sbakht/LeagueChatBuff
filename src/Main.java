
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
					
	            	if(message.equals("ob")) { //our blue
	            		for(Friend f : timerFriends) {
//		            		f.sendMessage("Our Blue Buff Killed");
	            		}
	            		Timer timer = new Timer();
	            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"BLUE", 300), 0, 60*1000);
	            	}
					
	            	if(message.equals("or")) { //our red
	            		for(Friend f : timerFriends) {
//		            		f.sendMessage("Our Blue Buff Killed");
	            		}
	            		Timer timer = new Timer();
	            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"RED", 300), 0, 60*1000);
            		}
	            	if(message.equals("tb")) { //their blue
//	            		saad.sendMessage("Their Blue Buff Killed");
//	            		vishal.sendMessage("Their Blue Buff Killed");
	            		Timer timer = new Timer();
	            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"ENEMEY BLUE", 300), 0, 60*1000);
	            	}
	            	if(message.equals("tr")) { //their red
//	            		saad.sendMessage("Their Red Buff Killed");
//	            		vishal.sendMessage("Their Red Buff Killed");
	            		Timer timer = new Timer();
	            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"ENEMY RED", 300), 0, 60*1000);
	            	}
	            	if(message.equals("drag")) { //dragon
//	            		saad.sendMessage("Dragon Killed");
//	            		vishal.sendMessage("Dragon Killed");
	            		Timer timer = new Timer();
	            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"DRAGON", 360), 0, 60*1000);
	            	}
	            	if(message.equals("baron")) { //our red
//	            		saad.sendMessage("Baron Killed");
//	            		vishal.sendMessage("Baron Killed");
	            		Timer timer = new Timer();
	            		timer.scheduleAtFixedRate(new BuffTimer(timerFriends,"BARON", 420), 0, 60*1000);
	            	}
					if(message.equals("disconnect")) {
						discon = true;
					}
					System.out.println(discon);

				}
			});
		    
//			api.disconnect();
		    
		}
	}

}
