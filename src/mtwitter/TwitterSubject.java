package mtwitter;

import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Ian Burton
 *
 */
//abstract class that makes sure any subject conforms to the DefaultMutableTreeNode component
//allows observers to register / follow subjects and get updates from them
public abstract class TwitterSubject extends DefaultMutableTreeNode implements AcceptVisitor {
	
	private static final long serialVersionUID = 42L;
	private List<TwitterObserver> followers;
	
	public TwitterSubject()
	{
		super();
		followers = new LinkedList<TwitterObserver>();
	}
	
	public void addObserver(TwitterObserver ob)
	{
		followers.add(ob);
	}
	
	public void notifyObservers()
	{
		for(TwitterObserver ob : followers)
		{
			ob.update(this);
		}
	}
	
}
