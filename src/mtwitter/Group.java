package mtwitter;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Ian Burton
 *
 */
//Group class that utilizes "Component" from Composite pattern as DefaultMutableTreeNode
//Since the point of the composite pattern is to form a tree-like structure anyways
//Allows us to have any children that are also TreeNodes, in this case Group and User
public class Group extends DefaultMutableTreeNode implements AcceptVisitor {

	private static final long serialVersionUID = 44L;
	
	public Group(String ID) 
	{
		super();
		super.setAllowsChildren(true);
		super.setUserObject(ID);
	}

	@Override
	public void accept(TwitterVisitor visitor) {//allow visitor to reach children nodes
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = this.children();
		
		while(e.hasMoreElements())
		{
			((AcceptVisitor)e.nextElement()).accept(visitor);
		}
	}

}
