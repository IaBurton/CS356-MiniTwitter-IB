package mtwitter;

/**
 * @author Ian Burton
 *
 */
//Simple interface that makes sure we can update on a notifyObservers call from a subject
public interface TwitterObserver {
	public void update(TwitterSubject subject);
}
