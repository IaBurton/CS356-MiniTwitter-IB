package mtwitter;

import java.util.LinkedList;
import java.util.List;

import mtwitterGUI.UserPanel;

/**
 * @author Ian Burton
 *
 */
//Main User class that extends subject and can observe other users as well
public class User extends TwitterSubject implements TwitterObserver {

	private static final long serialVersionUID = 43L;
	private List<String> followings, newsFeed;
	private String message;
	
	//These could potentially be in Subject; Added for assignment 3
	private long creationTime, lastUpdateTime;
	
	//Did not originally plan to have this reference in User
	//But after discovering the difficulty in updating the UI after it has been opened, closed and reopened
	//It seemed the best solution was to keep only one user panel per user, a sort-of singleton pattern
	//Connecting a single user to a single panel that can be reused
	private UserPanel panelInstance = null;
	
	public User(String ID) 
	{//Instantiate user and make sure we cannot have child nodes
		super();
		super.setAllowsChildren(false);
		super.setUserObject(ID);
		followings = new LinkedList<String>();
		newsFeed = new LinkedList<String>();
		creationTime = System.currentTimeMillis();
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public UserPanel getUserPanel()
	{//lazy instantiation on user panel and return it 
		if(panelInstance == null)
		{
			panelInstance = new UserPanel(this);
			//We want to see our own updates
			this.addObserver(panelInstance);
		}
			
		return panelInstance;
	}
	
	public String getID()
	{//returns ID for this user
		return (String) super.getUserObject();
	}
	
	public long getCreationTime()
	{//return creationTime; Added for assignment 3
		return creationTime;
	}
	
	public void setLastUpdateTime()
	{//setupdatetime; Added for assignment 3
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public long getLastUpdateTime()
	{
		return lastUpdateTime;
	}
	
	public void setCurrentTweet(String tweet)
	{//sets current tweet and adds it to this newsfeed
		message = tweet;
		newsFeed.add(0, this.getID() + ": " + message);
		//Added for assignment 3
		setLastUpdateTime();
	}
	
	public String getCurrentTweet()
	{
		return message;
	}
	
	public List<String> getNewsFeed()
	{
		return newsFeed;
	}
	
	public List<String> getFollowings()
	{
		return followings;
	}
	
	public void addToFollowings(String followedUser)
	{
		followings.add(followedUser);
	}

	@Override
	public void update(TwitterSubject subject) {
		if(subject instanceof User)
		{//updates this newsfeed when someone we follow has sent a tweet
			newsFeed.add(0, ((User)subject).getID() + ": " + ((User)subject).getCurrentTweet());
			//Added for assignment 3
			setLastUpdateTime();
		}
	}

	@Override
	public void accept(TwitterVisitor visitor) {//make sure visitor has access to us
		visitor.visitNode(this);
	}
	
}
