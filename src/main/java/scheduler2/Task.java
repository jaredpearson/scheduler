package scheduler2;

/**
 * Represents some task that needs to be performed
 * @author Jared Pearson
 */
public interface Task {
	public int getId();
	public boolean isFinished();
	public void exec();
}