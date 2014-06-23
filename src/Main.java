
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.github.theholywaffle.lolchatapi.ChatServer;
import com.github.theholywaffle.lolchatapi.LolChat;

public class Main {

	static LolChat api;
	static JButton logButton;

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
			BuffBot buffBot = new BuffBot();
			if(logButton.getText().equals("Login")) {
				api = new LolChat(ChatServer.NA, true);
				Account account = new Account();
				account.login(username.getText(), password.getText(), api);
				if(account.isLoggedIn()) {
					logButton.setText("Logout");
					buffBot.ChatBuffBot(username.getText(), password.getText(), api);
				}else{
					JOptionPane.showMessageDialog(null, "Login Failed");
				}
			}else{
				api.disconnect();
				logButton.setText("Login");
				JOptionPane.showMessageDialog(null, "Logout Successful");
			}

		}

	}

	public static void main(String args[]) {

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
