package mtwitter;

/**
 * @author Ian Burton
 *
 */
//Simple interface to make sure we can accept visitors that want to perform an operation
public interface AcceptVisitor {
	public void accept(TwitterVisitor visitor);
}
