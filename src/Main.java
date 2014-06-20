
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.github.theholywaffle.lolchatapi.ChatServer;
import com.github.theholywaffle.lolchatapi.LolChat;
import com.github.theholywaffle.lolchatapi.listeners.ChatListener;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;

public class Main {

	static private Hashtable<String, Objective> objectives = new Hashtable<String, Objective>();
	static private Hashtable<String, Integer> summonerSpells = new Hashtable<String, Integer>();
	static private Hashtable<String, ArrayList<Friend>> groupList = new Hashtable<String, ArrayList<Friend>>();
	static private Hashtable<Friend, String> usersInAGroup = new Hashtable<Friend, String>();
	
	static LolChat api;
	static JButton logButton;
	
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
	
	static class Action implements ActionListener {
		
		private JTextField username;
		private JPasswordField password;
		
		public Action(JTextField username, JPasswordField password) {
			// TODO Auto-generated constructor stub
			this.username = username;
			this.password = password;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(logButton.getText().equals("Login")) {
				BuffBot(username.getText(), password.getText());
			}else{
				api.disconnect();
				JOptionPane.showMessageDialog(null, "Logout Successful");
			}

		}
		
	}
	
	public static void BuffBot(String username, String password) {
		setUpObjectives();

		api = new LolChat(ChatServer.NA, true);
		
		if (api.login(username, password)) {
			
		    try {
		        Thread.sleep(1000); // Give server some time to send us all the data
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    
			JOptionPane.showMessageDialog(null, "Login Successful");	
			logButton.setText("Logout");
			
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

					
					ScheduledExecutorService timer = Executors.newScheduledThreadPool(5);
					if(objectives.containsKey(message)){
						//Cases are unnecessary, they were only for canceling existing timers
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message), sf), 0, 60, TimeUnit.SECONDS);
						switch (message) {
							case "ob":
//								for(Friend f : timerFriends) {
//									f.cancelTimer("ob");
//								}
//								friend.resetTimer("ob");
//								friend.getTimer("ob").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
//								timer.scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000, TimeUnit.SECONDS);
								break;
							case "or":
//								for(Friend f : timerFriends) {
//									f.cancelTimer("or");
//								}
//								friend.resetTimer("or");
//								friend.getTimer("or").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
							case "tb":
//								for(Friend f : timerFriends) {
//									f.cancelTimer("tb");
//								}
//								friend.resetTimer("tb");
//								friend.getTimer("tb").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
							case "tr":
//								for(Friend f : timerFriends) {
//									f.cancelTimer("tr");
//								}
//								friend.resetTimer("tr");
//								friend.getTimer("tr").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
							case "drag":
//								for(Friend f : timerFriends) {
//									f.cancelTimer("drag");
//								}
//								friend.resetTimer("drag");
//								friend.getTimer("drag").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;
							case "baron":
//								for(Friend f : timerFriends) {
//									f.cancelTimer("baron");
//								}
//								friend.resetTimer("baron");
//								friend.getTimer("baron").scheduleAtFixedRate(new BuffTimer(timerFriends, objectives.get(message)), 0, 60*1000);
								break;	
						}
					}
					
					
					if(message.toLowerCase().contains("flash")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends, new Objective(message, summonerSpells.get("flash"),false),sf), 0, 60, TimeUnit.SECONDS);
					}
					
					if(message.toLowerCase().contains("ignite") || message.contains("exhaust") || message.contains("ghost")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends,new Objective(message, summonerSpells.get("exhaust"),false),sf), 0, 60, TimeUnit.SECONDS);
					}
					
					if(message.toLowerCase().contains("heal")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends, new Objective(message, summonerSpells.get("heal"),false),sf), 0, 60, TimeUnit.SECONDS);
					}
					
					if(message.toLowerCase().contains("ward")) {
						timer.scheduleAtFixedRate(new BuffTimer(timerFriends, new Objective(message, 180,false),sf), 0, 60, TimeUnit.SECONDS);
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
							timer.scheduleAtFixedRate(new BuffTimer(timerFriends,new Objective(message, time, false),sf), extraTime, 60, TimeUnit.SECONDS);
						}else{
							friend.sendMessage("Your message needs to end with a valid number");
						}
					}
					
					if(message.startsWith("minimap on")) {
						String timeStr = message.substring(message.lastIndexOf(" ")+1);
						if(isNumeric(timeStr)) {
							int time = Integer.parseInt(timeStr);
							message = message.substring(message.indexOf(" ")+1,message.lastIndexOf(" "));
							timer.scheduleAtFixedRate(new MinimapTimer(friend), 0, time, TimeUnit.SECONDS);
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
		    
		}else{
			JOptionPane.showMessageDialog(null, "Login Failed");
		}
	}
	
	
	public static void main(String args[]) {
		
//		ScheduledExecutorService timer =
//		        Executors.newScheduledThreadPool(1);
//		ScheduledFuture<?> sf = null;
//		sf = timer.scheduleAtFixedRate(new Kappa(sf), 0, 1, TimeUnit.SECONDS);
//		scheduledExecutorService.scheduleAtFixedRate(new Kappa(), 0, 2, TimeUnit.SECONDS);
		
//		scheduledExecutorService.scheduleAtFixedRate(new Kappa(), 0, 1, TimeUnit.SECONDS);
//		ScheduledFuture bob = ScheduledFuture();
//		ScheduledFuture<?> scheduledFuture =
//			    timer.scheduleAtFixedRate(new Kappa(), 0, 1, TimeUnit.SECONDS);
//		scheduledFuture.
//		scheduledFuture1.cancel(true);
//		scheduledFuture.sh
//		scheduledExecutorService.shutdown();
//		scheduledExecutorService.shutdownNow();
		
		JFrame frame = new JFrame("League Chat Buff");
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new GridLayout(3, 1)); //3 rows on gui
		frame.add(panel);

		JLabel labelUsername = new JLabel("Username");
		JLabel labelPassword = new JLabel("Password");
		JLabel emptyLabel = new JLabel();
		JTextField username = new JTextField(20);
		JPasswordField password = new JPasswordField(20);
		panel.add(labelUsername);
		panel.add(username);
		panel.add(labelPassword);
		panel.add(password);
		panel.add(emptyLabel);
		logButton = new JButton("Login");
		panel.add(logButton);
		logButton.addActionListener(new Action(username, password));

        frame.pack();
        frame.setVisible(true);
		
		
	}

}
