package mtwitterGUI;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JList;

/**
 * @author Ian Burton
 *
 */
public class UserPanel {

	private JFrame frame;
	
	/**
	 * Create the application.
	 */
	public UserPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("User Panel");
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea userID = new JTextArea();
		userID.setToolTipText("User ID");
		userID.setBounds(12, 12, 194, 50);
		frame.getContentPane().add(userID);
		
		JButton followUser = new JButton("Follow User");
		followUser.setBounds(218, 12, 160, 50);
		frame.getContentPane().add(followUser);
		
		JList followList = new JList();
		followList.setToolTipText("Current Following");
		followList.setBounds(12, 68, 366, 99);
		frame.getContentPane().add(followList);
		
		JTextArea tweet = new JTextArea();
		tweet.setToolTipText("Tweet Message");
		tweet.setBounds(12, 179, 237, 50);
		frame.getContentPane().add(tweet);
		
		JButton postTweet = new JButton("Post Tweet");
		postTweet.setBounds(261, 179, 117, 50);
		frame.getContentPane().add(postTweet);
		
		JList newsFeed = new JList();
		newsFeed.setToolTipText("News Feed");
		newsFeed.setBounds(12, 235, 366, 125);
		frame.getContentPane().add(newsFeed);
	}
}
