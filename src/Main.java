import java.io.ObjectInputStream.GetField;
import java.util.Timer;

import com.github.theholywaffle.lolchatapi.ChatServer;
import com.github.theholywaffle.lolchatapi.LolChat;
import com.github.theholywaffle.lolchatapi.listeners.ChatListener;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class Main {
	
	static private boolean discon;
	
	public static void main(String args[]) {

		LolChat api = new LolChat(ChatServer.NA, false);
		if (api.login("sealsmurf1", "sealsmurf2")) {

		    try {
		        Thread.sleep(1000); // Give server some time to send us all the data
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    
//		    System.out.println(api.getFriendByName("iwanthotdogs"));
//		    System.out.println(api.getFriendByName("iwanthotdogs").getName());
		    
		    //Example 2: Adding ChatListener - listens to chat messages from any of your friends.
			api.addChatListener(new ChatListener() {

				public void onMessage(Friend friend, String message) {
					message = (String) message.trim();
					System.out.println("[All]>" + friend.getName() + ": " + message);
					
	            	if(message.equals("ob")) { //our blue
	            		friend.sendMessage("Our Blue Buff Killed");
	            		Timer timer = new Timer();
	            		timer.scheduleAtFixedRate(new BuffTimer(friend,"Blue", 10), 0, 5*1000);
	            	}
					
	            	if(message.equals("or")) { //our red
	            		friend.sendMessage("Our Red Buff Killed");
	            	}
	            	if(message.equals("tb")) { //their blue
	            		friend.sendMessage("Their Blue Buff Killed");
	            	}
	            	if(message.equals("tr")) { //their red
	            		friend.sendMessage("Their Red Buff Killed");
	            	}
	            	if(message.equals("drag")) { //dragon
	            		friend.sendMessage("Dragon Killed");
	            		Timer timer = new Timer();
	            		timer.scheduleAtFixedRate(new BuffTimer(friend,"Dragon", 360), 0, 5*1000);
	            	}
	            	if(message.equals("baron")) { //our red
	            		friend.sendMessage("Baron Killed");
	            	}
					if(message.equals("disconnect")) {
						discon = true;
					}
					System.out.println(discon);

				}
			});
		    
//			api.disconnect();
		    
		    // Example 2: Send Chat Message to all your friends and wait for an response
//		    for (Friend f : api.getFriends()) {
//		    	f.sendMessage("hi");
//		    	while(f.getName().equals("iwanthotdogs")) {
//		    		//f.sendMessage("hi hotdogs");
//		         new ChatListener() {
//
//		            @Override
//		            public void onMessage(Friend friend, String message) {
//		            	System.out.println("onMessage");
//		            	message = (String) message.trim();
//		            	if(message.equals("ob")) { //our blue
//		            		friend.sendMessage("Our Blue Buff Killed");
////		            		Timer timer = new Timer();
////		            		timer.schedule(new BuffTimer(friend), 5*1000);
////		            		timer.scheduleAtFixedRate(new BuffTimer(friend,"Blue", 300), 0, 5*1000);
//		            	}
//		            	if(message.equals("or")) { //our red
//		            		friend.sendMessage("Our Red Buff Killed");
//		            	}
//		            	if(message.equals("tb")) { //their blue
//		            		friend.sendMessage("Their Blue Buff Killed");
//		            	}
//		            	if(message.equals("tr")) { //their red
//		            		friend.sendMessage("Their Red Buff Killed");
//		            	}
//		            	if(message.equals("drag")) { //dragon
//		            		friend.sendMessage("Dragon Killed");
//		            	}
//		            	if(message.equals("baron")) { //our red
//		            		friend.sendMessage("Baron Killed");
//		            	}
//	            	}
//		        };
//		    	}
//		    }
		}
	}

}
