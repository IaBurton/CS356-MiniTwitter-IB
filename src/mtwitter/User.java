package mtwitter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ian Burton
 *
 */
public class User extends TwitterSubject implements TwitterObserver {

	private static final long serialVersionUID = 43L;
	private List<String> followings, newsFeed;
	private String message;
	
	public User(String ID) 
	{
		super();
		super.setAllowsChildren(false);
		super.setUserObject(ID);
		followings = new LinkedList<String>();
		newsFeed = new LinkedList<String>();
	}
	
	public String getID()
	{
		return (String) super.getUserObject();
	}
	
	public void setCurrentTweet(String tweet)
	{
		message = tweet;
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
		{
			newsFeed.add(0, ((User)subject).getID() + ": " + ((User)subject).getCurrentTweet());
		}
	}
	
}
