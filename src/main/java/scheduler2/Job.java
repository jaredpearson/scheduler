package scheduler2;


/**
 * Job which is scheduled to run the Scheduler.
 * @author Jared Pearsonå
 */
public class Job implements Runnable {
	private final Task task;
	private final int priority;
	
	public Job(Task task, int priority) {
		this.task = task;
		this.priority = priority;
	}
	
	public int getTaskId() {
		return task.getId();
	}
	
	public boolean isFinished() {
		return this.task.isFinished();
	}
	
	public int getPriority() {
		return priority;
	}
	
	@Override
	public void run() {
		Console.writeMessage("Job starting");
		task.exec();
		if(task.isFinished()) {
			Console.writeMessage("Job finished");
		}
	}
}