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
import javax.swing.JLabel;

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
	
	private JLabel lastUpdateLabel;
	/**
	 * Create the application.
	 */
	public UserPanel(User user) {
		this.user = user;
		followListModel = new DefaultListModel<String>();
		newsFeedModel = new DefaultListModel<String>();
		
		updateModels();
		
		initialize();
	}
	
	//Set frame visibility
	public void setVisibility(boolean b)
	{
		if(b)
		{//updateModels and clear text fields if we're coming back into scope
			updateModels();
			userID.setText("");
			tweet.setText("");
		}
		
		frame.setVisible(b);
	}
	
	//Updates models that dynamically update UI as things happen when UI is open
	//Also helps if same user panel is opened/closed/reopened
	public void updateModels()
	{//need to clear elements or will get repeats in model
	 //adding elements that are not already added would be an alternative
		followListModel.clear();
		newsFeedModel.clear();
		
		for(String s : user.getFollowings())
			followListModel.addElement(s);
		
		for(String s : user.getNewsFeed())
			newsFeedModel.addElement(s);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Added creationTime to title of window / panel. Probably not the best place but easy enough for now
		frame = new JFrame("User Panel: " + user.getID() + " Joined: " + user.getCreationTime());
		frame.setBounds(100, 100, 400, 428);
		//Hide instead of exit so we dont quit the entire application on the closing of a user panel
		//Also allows same panel to be reused
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		
		//Added for assignment 3
		lastUpdateLabel = new JLabel("New label");
		lastUpdateLabel.setBounds(12, 373, 366, 15);
		lastUpdateLabel.setText("Last Update At: " + user.getLastUpdateTime());
		frame.getContentPane().add(lastUpdateLabel);
	}
	
	private User findUserByID(String ID)
	{//Finds another user by their ID, this method could exist in the User class or could be a visitor
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
	
	//Checks if one user is already following a user
	private boolean checkIfNotFollowing(User check)
	{//Or is trying to follow themselves, this method could also be put into User class
		if(check == null || check.getID().equals(user.getID()))
			return false;
		
		for(String s : user.getFollowings())
			if(check.getID().equals(s))
				return false;
		
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {//event handler for buttons of userpanel
		switch (ae.getActionCommand())
		{
			case "followUser":
			{//follow a user
				if(!userID.getText().equals(""))
				{//find user and make sure we can follow them
					User found = findUserByID(userID.getText());
					boolean check = checkIfNotFollowing(found);
					
					if(check)
					{
						user.addToFollowings(found.getID());
						followListModel.addElement(found.getID());
						found.addObserver(user);
						found.addObserver(this);//not sure if this is how we should update UI
					}
					else
						JOptionPane.showMessageDialog(null, "No User found with that ID, or already following that User.",
								"Information", JOptionPane.INFORMATION_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "No User ID entered.",
							"Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
			case "postTweet":
			{//Send tweet, make sure it isnt empty or greater than 140 char
				if(!tweet.getText().equals("") && (tweet.getText().length() <= 140))
				{
					user.setCurrentTweet(tweet.getText());
					user.notifyObservers();
				}
				else
					JOptionPane.showMessageDialog(null, "No tweet entered, or tweet too long.",
							"Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
		
	}

	@Override
	public void update(TwitterSubject subject) {//updateUI when new tweet is sent out
		if(subject instanceof User)
		{//add to front of model so new tweets appear at the top
			newsFeedModel.add(0, ((User)subject).getID() + ": " + ((User)subject).getCurrentTweet());
			lastUpdateLabel.setText("Last Update At: " + user.getLastUpdateTime());
		}
	}
}
