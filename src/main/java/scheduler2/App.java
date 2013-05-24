package scheduler2;

/**
 * Main class/entry point for the application
 * @author Jared Pearson
 */
public class App {
	
	public static void main(String[] args) {
		
		//build a scheduler and preload it with two jobs at a priority of 5
		//the higher the number for priority, the earlier it will be executed.
		PriorityScheduler scheduler = new PriorityScheduler();
		scheduler.addJob(new Job(new MockTask(0), 5));
		scheduler.addJob(new Job(new MockTask(1), 5));
		
		Console.writeMessage("Starting scheduler");
		Thread schedulerThread = new Thread(scheduler);
		schedulerThread.start();
		
		//after ~1500 millis we want to interupt the scheduler with a higher priority job
		TimedJobAdditionService jobAdditionService = new TimedJobAdditionService(scheduler, new Job(new MockTask(2), 10), 1500);
		jobAdditionService.start();
		
		//start the main execution loop
		while(true) {
			
			//check the scheduler every 100 millis to see if it has jobs
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}
			
			//if the scheduler doesn't have any jobs left, we can stop the main execution loop
			if(!scheduler.hasJobs()) {
				break;
			}
		}
		
		Console.writeMessage("Stopping scheduler");
		schedulerThread.interrupt();
	}
	
	/**
	 * This service simulates a new job being added to the scheduler at specific point in the time.
	 * @author Jared Pearson &lt;jaredapearson@gmail.com&gt;
	 */
	private static final class TimedJobAdditionService extends Thread {
		private final PriorityScheduler scheduler; 
		private final long millis;
		private final Job job;
		
		public TimedJobAdditionService(PriorityScheduler scheduler, Job job, long millis) {
			this.scheduler = scheduler;
			this.millis = millis;
			this.job = job;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(millis);
				scheduler.addJob(this.job);
			} catch (InterruptedException e) {
			}
		}
	}
}
