import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.github.theholywaffle.lolchatapi.LolChat;
import com.github.theholywaffle.lolchatapi.listeners.ChatListener;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;


public class BuffBot {

	static private Hashtable<String, Objective> objectives = new Hashtable<String, Objective>();
	static private Hashtable<String, Integer> summonerSpells = new Hashtable<String, Integer>();
	static private Hashtable<String, ArrayList<Friend>> groupList = new Hashtable<String, ArrayList<Friend>>();
	static private Hashtable<Friend, String> usersInAGroup = new Hashtable<Friend, String>();
	
	private final int NOTIFICATION_INTERVAL = 60;

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


	public void ChatBuffBot(String username, String password, LolChat api) {
		setUpObjectives();

		try {
			Thread.sleep(1000); // Give server some time to send us all the data
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "Login Successful");

		api.addChatListener(new ChatListener() {

			public void onMessage(Friend friend, String message) {
				ScheduledFuture<?> sf = null;
				message = (String) message.trim();
				System.out.println("[All]>" + friend.getName() + ": " + message);

				ArrayList<Friend> timerFriends = new ArrayList<Friend>();	

				if(usersInAGroup.containsKey(friend)) {
					String groupName = usersInAGroup.get(friend);
					timerFriends = groupList.get(groupName);
				}else{
					timerFriends.add(friend); //sends message to themselves if not in a group
				}

				//Depreciated - Kept for minimap to keep working
				ScheduledExecutorService miniMapTimer= Executors.newScheduledThreadPool(1);
				
				MyTimer timer = new MyTimer();
				if(objectives.containsKey(message)){
					timer.createTimer(new BuffTimer(timerFriends, objectives.get(message), sf));
				}


				if(message.toLowerCase().contains("flash")) {
					timer.createTimer(new BuffTimer(timerFriends, new Objective(message, summonerSpells.get("flash"),false),sf));
				}

				if(message.toLowerCase().contains("ignite") || message.contains("exhaust") || message.contains("ghost")) {
					timer.createTimer(new BuffTimer(timerFriends,new Objective(message, summonerSpells.get("exhaust"),false),sf));
				}

				if(message.toLowerCase().contains("heal")) {
					timer.createTimer(new BuffTimer(timerFriends, new Objective(message, summonerSpells.get("heal"),false),sf));
				}

				if(message.toLowerCase().contains("ward")) {
					timer.createTimer(new BuffTimer(timerFriends, new Objective(message, 180,false),sf));
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

				//Ex: custom shen ult 200
				//EX: custom shen ult 20% 200
				//Can have it account for cooldowns if you know enemy cooldown
				if(message.startsWith("custom")) { 
					String timeStr = message.substring(message.lastIndexOf(" ")+1);
					if(isNumeric(timeStr)) {
						int time = Integer.parseInt(timeStr);

						Pattern p = Pattern.compile("[0-9]+%");
						Matcher m = p.matcher(message);

						if (m.find()) {
							double percentage = Integer.parseInt(m.group(0).substring(0, m.group(0).length() - 1)) / 100.0;
							time-= time * percentage;
						}

						int extraTime = time % 60; //Fixes issue with fraction of minute timers not timing correctly
						time-= extraTime;
						message = message.substring(message.indexOf(" ")+1,message.lastIndexOf(" "));
						timer.createTimer(new BuffTimer(timerFriends,new Objective(message, time, false),sf), extraTime);
					}else{
						friend.sendMessage("Your message needs to end with a valid number");
					}
				}

				if(message.startsWith("minimap on")) {
					String timeStr = message.substring(message.lastIndexOf(" ")+1);
					if(isNumeric(timeStr)) {
						int time = Integer.parseInt(timeStr);
						message = message.substring(message.indexOf(" ")+1,message.lastIndexOf(" "));
						miniMapTimer.scheduleAtFixedRate(new MinimapTimer(friend), 0, time, TimeUnit.SECONDS);
					}else{
						friend.sendMessage("Your message needs to end with a valid number");
					}
				}

				if(message.equals("minimap off")) {
					//						friend.cancelTimer("minimap");;
				}

			}
		});

		//		    api.disconnect();

	}

}
