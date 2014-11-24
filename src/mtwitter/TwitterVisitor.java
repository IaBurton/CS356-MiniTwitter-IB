package mtwitter;

/**
 * @author Ian Burton
 *
 */
//Simple interface to make sure we can visit subject nodes and perform operations on them
//and display the results
public interface TwitterVisitor {
	public void visitNode(TwitterSubject node);
	public void displayResult();
}
