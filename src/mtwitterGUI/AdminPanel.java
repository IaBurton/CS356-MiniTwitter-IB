package mtwitterGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import mtwitter.GoodVisitor;
import mtwitter.Group;
import mtwitter.LastUpdatedVisitor;
import mtwitter.TweetVisitor;
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
	
	//Counters for simple analytics, groupCounter starts at 1 because of root
	private int userCounter = 0, groupCounter = 1;
	
	//Private hashmaps to ensure creation of unique users and groups
	//If a user or group exists with that name / ID, append the ID
	//with the number of those users, starts at 1 based indexing
	//this is how Facebook does it, not sure about Twitter
	private HashMap<String,Integer> uniUser, uniGroup;
	
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
		
		uniUser = new HashMap<String,Integer>();
		uniGroup = new HashMap<String,Integer>();
		
		//Create a root add it to used names, then make it the root of the jtree
		DefaultMutableTreeNode root = new Group("ROOT");
		uniGroup.put("ROOT", 1);
		
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
		userTotal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//Simple inner actionlistner to return user total with joptionpane
				JOptionPane.showMessageDialog(null, "User Total: " + userCounter,
						"Users", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		frame.getContentPane().add(userTotal);
		
		JButton groupTotal = new JButton("Show Group Total");
		groupTotal.setBounds(405, 176, 195, 40);
		groupTotal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//Same as above but for group total
				JOptionPane.showMessageDialog(null, "Group Total: " + groupCounter,
						"Groups", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		frame.getContentPane().add(groupTotal);
		
		JButton messageTotal = new JButton("Show Messages Total");
		messageTotal.setBounds(204, 218, 195, 40);
		messageTotal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//Same as above but for tweet total
				TweetVisitor tv = new TweetVisitor();
				((Group)root).accept(tv);
				tv.displayResult();
			}
		});
		frame.getContentPane().add(messageTotal);
		
		JButton positivePercent = new JButton("Positive Percentage");
		positivePercent.setBounds(405, 218, 195, 40);
		positivePercent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//Same as above but for percentage positive
				GoodVisitor gv = new GoodVisitor();
				((Group)root).accept(gv);
				gv.displayResult();
			}
		});
		frame.getContentPane().add(positivePercent);
		
		//Added for assignment 3
		JButton showLastUpdated = new JButton("Show Last User Updated");
		showLastUpdated.setBounds(204, 134, 384, 25);
		showLastUpdated.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LastUpdatedVisitor lu = new LastUpdatedVisitor();
				((Group)root).accept(lu);
				lu.displayResult();
			}
		});
		frame.getContentPane().add(showLastUpdated);
		
		//frame.setVisible(true); Not sure if this should be here or in Driver, made a method just in case
	}

	@Override
	public void actionPerformed(ActionEvent ae) {//actionevent handler for most buttons
		
		//Get last selected node from tree, if no tree has been selected (program just started for example)
		//Then set the last selected node as the root of the tree
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if(selectedNode == null)
			selectedNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
		
		switch (ae.getActionCommand())
		{//Similar logic in the first two cases, probably a code smell
			case "addUser":
			{//add new user when add user button pressed
				try
				{//add new user with uniqueID, updateUI the increment total users counter
					selectedNode.add(new User(getUnique(uniUser,txtUserID.getText())));
					tree.updateUI();
					userCounter++;
				}
				catch (Exception e)
				{//user is not allowed to have children nodes, this catches that case
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "You cannot add a User to another User.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
			case "addGroup":
			{//add new group when add group button pressed
				try
				{
					selectedNode.add(new Group(getUnique(uniGroup,txtGroupID.getText())));
					tree.updateUI();
					groupCounter++;
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
			{//open user view with userpanel instance attached to user, singleton userpanel for a specific user
				if(selectedNode instanceof User)
				{
					User us = (User) selectedNode;
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							try {
								us.getUserPanel().setVisibility(true);
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
	
	private String getUnique(HashMap<String,Integer> map, String ID)
	{	
		//Remove whitespace from ID; Added for assignment 3
		ID = ID.replaceAll("\\s","");
		//returns unique string for user/group ID
		if(map.containsKey(ID))
		{
			int c = map.get(ID) + 1;
			map.put(ID, c);
			return ID + "." + c;
		}
		else
		{
			map.put(ID, 1);
			return ID;
		}
	}
}
