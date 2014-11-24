package mtwitter;

import javax.swing.JOptionPane;
//Visitor that displays percentage of tweets with good/positive words
public class GoodVisitor implements TwitterVisitor {

	private int tweetCounter = 0, goodCounter = 0;
	private static final String[] goodWords = {"good", "great", "excellent", "fantastic", "amazing", "wonderful"};

	@Override
	public void visitNode(TwitterSubject node) {
		
		//we dont care if it isnt a user so return otherwise
		if(!(node instanceof User))
			return;
		
		for(String s : ((User)node).getNewsFeed())
		{//always increment total tweets
			tweetCounter++;
			for(String gw : goodWords)
			{//increment "good" tweets only when they have a good word
				if(s.toLowerCase().contains(gw))
				{
					goodCounter++;
					break;
				}
			}
		}
	}

	@Override
	public void displayResult() {
		JOptionPane.showMessageDialog(null, "Percentage of Positive Tweets: " + ((goodCounter * 100.0f) / tweetCounter),
				"Positive Tweets", JOptionPane.INFORMATION_MESSAGE);
	}

}