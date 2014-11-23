package mtwitter;

/*import java.util.LinkedList;
import java.util.List;*/

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Ian Burton
 *
 */
public class Group extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 44L;
	
	public Group(String ID) 
	{
		super();
		super.setAllowsChildren(true);
		super.setUserObject(ID);
	}

}
