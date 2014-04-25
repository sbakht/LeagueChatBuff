
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;

import com.github.theholywaffle.lolchatapi.ChatServer;
import com.github.theholywaffle.lolchatapi.LolChat;
import com.github.theholywaffle.lolchatapi.listeners.ChatListener;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class Main {
	
	static private Hashtable<String, Objective> objectives = new Hashtable<String, Objective>();
	static private Hashtable<String, Integer> summonerSpells = new Hashtable<String, Integer>();
	static private Hashtable<String, ArrayList<Friend>> groupList = new Hashtable<String, ArrayList<Friend>>();
	static private Hashtable<Friend, String> usersInAGroup = new Hashtable<Friend, String>();
	
	public static void setUpObjectives(){
		objectives.put("ob", new Objective("Blue", 300, true));
		objectives.put("or", new Objective("Red", 300, true));
		objectives.put("tb", new Objective("Enemy Blue", 300, true));
		objectives.put("tr", new Objective("Enemy Red", 300, true));
		objectives.put("drag", new Objective("Dragon", 360, true));
		objectives.put("baron", new Objective("Baron", 420, true));
		summonerSpells.put("exhaust", 210);
		summonerSpells.put("flash", 300);
		summonerSpells.put("heal", 240);
	}
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	public static void main(String args[]) {
		
		setUpObjectives();

		LolChat api = new LolChat(ChatServer.NA, true);
		
		if (api.login("sealsmurf1", "sealsmurf2")) {

		    try {
		        Thread.sleep(1000); // Give server some time to send us all the data
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }

		    api.addChatListener(new ChatListener() {
				
				public void onMessage(Friend friend, String message) {
					
					message = (String) message.trim();
					System.out.println("[All]>" + friend.getName() + ": " + message);
					
					ArrayList<Friend> timerFriends = new ArrayList<Friend>();	
					
					if(usersInAGroup.containsKey(friend)) {
						String groupName = usersInAGroup.get(friend);
						timerFriends = groupList.get(groupName);
					}else{
						timerFriends.add(friend); //sends message to themselves if not in a group
					}

					
					Timer timer = new Timer();
					if(objectives.containsKey(message)){
						switch (message) {
							case "ob":
								friend.resetTimer("ob");
								friend.getTimer("ob").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
							case "or":
								friend.resetTimer("or");
								friend.getTimer("or").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
							case "tb":
								friend.resetTimer("tb");
								friend.getTimer("tb").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
							case "tr":
								friend.resetTimer("tr");
								friend.getTimer("tr").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
							case "drag":
								friend.resetTimer("drag");
								friend.getTimer("drag").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
							case "baron":
								friend.resetTimer("baron");
								friend.getTimer("baron").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
								
						}
					}
					
					
					if(message.toLowerCase().contains("flash")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends, new Objective(message, summonerSpells.get("flash"),false)), 0, 60*1000);
					}
					
					if(message.toLowerCase().contains("ignite") || message.contains("exhaust") || message.contains("ghost")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends,new Objective(message, summonerSpells.get("exhaust"),false)), 0, 60*1000);
					}
					
					if(message.toLowerCase().contains("heal")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends, new Objective(message, summonerSpells.get("heal"),false)), 0, 60*1000);
					}
					
					if(message.startsWith("group")) { //Ex: group The_Best_Group
						ArrayList<Friend> groupFriends;
						
						if(groupList.get(message) != null) {
							groupFriends = groupList.get(message);
						}else{
							groupFriends = new ArrayList<Friend>();
						}
						
						if(!usersInAGroup.containsKey(friend)) {
							groupFriends.add(friend);
							usersInAGroup.put(friend,message);
						}
						groupList.put(message, groupFriends);
						friend.sendMessage("Group Members:");
						for(Friend f : groupFriends) {
							friend.sendMessage(f.getName());
						}
					}
					
					if(message.equals("leave group")) { //Ex: leave group
						ArrayList<Friend> groupFriends;
						
						if(usersInAGroup.containsKey(friend)) {
							String group = usersInAGroup.get(friend);
							
							groupFriends = groupList.get(group);
							groupFriends.remove(friend);
							groupList.put(group, groupFriends);
							usersInAGroup.remove(friend);
							friend.sendMessage("You have been removed from the group");
						}else{
							friend.sendMessage("You never were in a group");
						}	
					}
					
					if(message.startsWith("custom")) { //Ex: custom shen ult 180
						String timeStr = message.substring(message.lastIndexOf(" ")+1);
						if(isNumeric(timeStr)) {
							int time = Integer.parseInt(timeStr);
							timer.scheduleAtFixedRate(new BuffTimer(timerFriends,new Objective(message, time, false)), 0, 60*1000);
						}else{
							friend.sendMessage("Your message needs to end with a valid number");
						}
					}
					
				}
			});
		  
//		    api.disconnect();
		    
		}
	}

}
