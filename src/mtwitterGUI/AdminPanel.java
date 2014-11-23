package mtwitterGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import mtwitter.Group;
import mtwitter.User;

/**
 * @author Ian Burton
 *
 */
public class AdminPanel implements ActionListener {

	private JFrame frame;
	private JTree tree;
	private JTextArea txtUserID, txtGroupID;
	private static AdminPanel instance = null;
	
	/**
	 * Create the application.
	 */
	private AdminPanel() {
		initialize();
	}
	
	//Get AdminPanel instance if already created / lazy instantiation
	public static AdminPanel getInstance()
	{
		if(instance == null)
			instance = new AdminPanel();
		
		return instance;
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
		frame = new JFrame("Admin Control Panel");
		frame.setBounds(100, 100, 600, 298);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		DefaultMutableTreeNode root = new Group("ROOT");
		
		tree = new JTree(root);
		tree.setBounds(12, 0, 180, 272);
		frame.getContentPane().add(tree);
		
		txtUserID = new JTextArea();
		txtUserID.setToolTipText("User ID");
		txtUserID.setBounds(204, 2, 195, 38);
		frame.getContentPane().add(txtUserID);
		
		txtGroupID = new JTextArea();
		txtGroupID.setToolTipText("Group ID");
		txtGroupID.setBounds(204, 45, 195, 38);
		frame.getContentPane().add(txtGroupID);
		
		JButton addUser = new JButton("Add User");
		addUser.setBounds(411, -3, 177, 40);
		addUser.addActionListener(this);
		addUser.setActionCommand("addUser");
		frame.getContentPane().add(addUser);
		
		JButton addGroup = new JButton("Add Group");
		addGroup.setBounds(411, 43, 177, 40);
		addGroup.addActionListener(this);
		addGroup.setActionCommand("addGroup");
		frame.getContentPane().add(addGroup);
		
		JButton userView = new JButton("Open User View");
		userView.setBounds(204, 97, 384, 25);
		userView.addActionListener(this);
		userView.setActionCommand("userView");
		frame.getContentPane().add(userView);
		
		JButton userTotal = new JButton("Show User Total");
		userTotal.setBounds(204, 176, 195, 40);
		frame.getContentPane().add(userTotal);
		
		JButton groupTotal = new JButton("Show Group Total");
		groupTotal.setBounds(405, 176, 195, 40);
		frame.getContentPane().add(groupTotal);
		
		JButton messageTotal = new JButton("Show Messages Total");
		messageTotal.setBounds(204, 218, 195, 40);
		frame.getContentPane().add(messageTotal);
		
		JButton positivePercent = new JButton("Positive Percentage");
		positivePercent.setBounds(405, 218, 195, 40);
		frame.getContentPane().add(positivePercent);
		
		//frame.setVisible(true); Not sure if this should be here or in Driver
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if(selectedNode == null)
			selectedNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
		
		switch (ae.getActionCommand())
		{
			case "addUser":
			{
				try
				{
					selectedNode.add(new User(txtUserID.getText()));
					tree.updateUI();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "You cannot add a User to another User.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
			case "addGroup":
			{
				try
				{
					selectedNode.add(new Group(txtGroupID.getText()));
					tree.updateUI();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "You cannot add a Group to a User.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
			case "userView":
			{
				if(selectedNode instanceof User)
				{
					User us = (User) selectedNode;
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							try {
								UserPanel user = new UserPanel(us);
								user.setVisibility(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
				else
					JOptionPane.showMessageDialog(null, "No User selected.",
							"Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
		
	}
}
