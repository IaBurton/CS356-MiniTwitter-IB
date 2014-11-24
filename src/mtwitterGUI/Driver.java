package mtwitterGUI;

import javax.swing.SwingUtilities;

/**
 * @author Ian Burton
 *
 */
public class Driver {

	/**
	 * Simple Driver class
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPanel admin = AdminPanel.getInstance();
					admin.setVisibility(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
