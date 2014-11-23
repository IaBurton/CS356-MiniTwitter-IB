package mtwitterGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.tree.DefaultMutableTreeNode;

import mtwitter.TwitterObserver;
import mtwitter.TwitterSubject;
import mtwitter.User;

/**
 * @author Ian Burton
 *
 */
public class UserPanel implements ActionListener, TwitterObserver {

	private JFrame frame;
	private JTextArea userID, tweet;
	private JList followList, newsFeed;
	private DefaultListModel<String> followListModel, newsFeedModel;
	private User user;
	
	/**
	 * Create the application.
	 */
	public UserPanel(User user) {
		this.user = user;
		followListModel = new DefaultListModel<String>();
		newsFeedModel = new DefaultListModel<String>();
		
		for(String s : user.getFollowings())
			followListModel.addElement(s);
		
		for(String s : user.getNewsFeed())
			newsFeedModel.addElement(s);
		
		initialize();
	}
	
	//Set frame visibility
	public void setVisibility(boolean b)
	{
		frame.setVisible(b);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("User Panel: " + user.getID());
		frame.setBounds(100, 100, 400, 400);
		//Dispose instead of exit so we dont quit the entire application on the closing of a user panel
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		userID = new JTextArea();
		userID.setToolTipText("User ID");
		userID.setBounds(12, 12, 194, 50);
		frame.getContentPane().add(userID);
		
		JButton followUser = new JButton("Follow User");
		followUser.setBounds(218, 12, 160, 50);
		followUser.addActionListener(this);
		followUser.setActionCommand("followUser");
		frame.getContentPane().add(followUser);
		
		followList = new JList(followListModel);
		followList.setToolTipText("Current Following");
		followList.setBounds(12, 68, 366, 99);
		frame.getContentPane().add(followList);
		
		tweet = new JTextArea();
		tweet.setToolTipText("Tweet Message");
		tweet.setBounds(12, 179, 237, 50);
		frame.getContentPane().add(tweet);
		
		JButton postTweet = new JButton("Post Tweet");
		postTweet.setBounds(261, 179, 117, 50);
		postTweet.addActionListener(this);
		postTweet.setActionCommand("postTweet");
		frame.getContentPane().add(postTweet);
		
		newsFeed = new JList(newsFeedModel);
		newsFeed.setToolTipText("News Feed");
		newsFeed.setBounds(12, 235, 366, 125);
		frame.getContentPane().add(newsFeed);
	}
	
	private User findUserByID(String ID)
	{
		@SuppressWarnings("unchecked")
	    Enumeration<DefaultMutableTreeNode> e = ((DefaultMutableTreeNode) user.getRoot()).depthFirstEnumeration();
	    while (e.hasMoreElements()) {
	        DefaultMutableTreeNode node = e.nextElement();
	        if ((node instanceof User) && ((User)node).getID().equals(ID)) {
	            return (User) node;
	        }
	    }
	    return null;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "followUser":
			{
				if(!userID.getText().equals(""))
				{
					User found = findUserByID(userID.getText());
					
					if(found != null)
					{
						user.addToFollowings(found.getID());
						followListModel.addElement(found.getID());
						found.addObserver(user);
						found.addObserver(this);//not sure if this is how we should update UI
					}
					else
						JOptionPane.showMessageDialog(null, "No User found with that ID.",
								"Not Found", JOptionPane.INFORMATION_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "No User ID entered.",
							"Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
			case "postTweet":
			{
				if(!tweet.getText().equals(""))
				{
					user.setCurrentTweet(tweet.getText());
					user.notifyObservers();
				}
				else
					JOptionPane.showMessageDialog(null, "No tweet entered.",
							"Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
		
	}

	@Override
	public void update(TwitterSubject subject) {
		if(subject instanceof User)
		{
			newsFeedModel.addElement(((User)subject).getID() + ": " + ((User)subject).getCurrentTweet());
		}
	}
}
