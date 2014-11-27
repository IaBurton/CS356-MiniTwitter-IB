package mtwitter;

import javax.swing.JOptionPane;

/**
 * @author Ian Burton
 *
 */
//Visitor that finds and displays the user who made the last update / updated last
//Added for assignment 3
public class LastUpdatedVisitor implements TwitterVisitor {

	private String ID = "No User Created";
	private long bestUpdateTime = 0;
	
	@Override
	public void visitNode(TwitterSubject node) {
		//Dont care if not a user node, so return
		if(!(node instanceof User))
			return;
		
		if(((User)node).getLastUpdateTime() > bestUpdateTime)
		{
			bestUpdateTime = ((User)node).getLastUpdateTime();
			ID = ((User)node).getID();
		}
	}

	@Override
	public void displayResult() {
		JOptionPane.showMessageDialog(null, "The last User to be updated was: " + ID,
				"Last Update", JOptionPane.INFORMATION_MESSAGE);
	}

}
