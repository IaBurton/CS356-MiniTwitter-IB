package mtwitter;

import javax.swing.JOptionPane;

/**
 * @author Ian Burton
 *
 */
//Visitor that counts total number of tweets from all news feeds
public class TweetVisitor implements TwitterVisitor {
	
	private int tweetCounter = 0;
	
	@Override
	public void visitNode(TwitterSubject node) {
		
		//Dont care if not a user node, so return
		if(!(node instanceof User))
			return;
		
		tweetCounter += ((User)node).getNewsFeed().size();
	}

	@Override
	public void displayResult() {
		JOptionPane.showMessageDialog(null, "Total Tweets in all news feeds: " + tweetCounter,
				"Tweets", JOptionPane.INFORMATION_MESSAGE);
	}

}
