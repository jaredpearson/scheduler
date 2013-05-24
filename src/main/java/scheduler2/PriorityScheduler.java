package scheduler2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Scheduler that prioritizes jobs to be run in priority order. If a new job is
 * added to the service when a job is currently being executed, then the job is
 * paused and the higher priority service is allowed to do work.
 * @author Jared Pearson
 */
public class PriorityScheduler implements Runnable {

	private List<Job> jobs = new ArrayList<>();
	private Job currentJob = null;
	private Thread currentJobThread = null;
	
	/**
	 * Determines if the scheduler has jobs
	 */
	public synchronized boolean hasJobs() {
		return !jobs.isEmpty();
	}
	
	/**
	 * Adds a job to the scheduler
	 */
	public synchronized void addJob(Job job) {
		this.jobs.add(job);
		
		//if a new job is added that is higher priority than the current job,
		//then we need to tell the current job to stop and let the scheduler
		//pick up the new job
		if(currentJob != null && job.getPriority() > currentJob.getPriority()) {
			Console.writeMessage("Interrupting current job due to higher priority job added");
			currentJobThread.interrupt();
			try {
				currentJobThread.join();
			} catch (InterruptedException e) {
			}
			this.currentJob = null;
			this.currentJobThread = null;
		}
		
		//sort the jobs in priority order
		Collections.sort(this.jobs, new Comparator<Job>() {
			@Override
			public int compare(Job job1, Job job2) {
				return job2.getPriority() - job1.getPriority();
			}
		});
	}
	
	@Override
	public void run() {
		Console.writeMessage("Scheduler started");
		
		//schedulers main loop
		while(true) {
			if(Thread.interrupted()) {
				break;
			}
			
			//if the current job is finished, then we need to start the next
			if(currentJob != null && currentJob.isFinished()) {
				Console.writeMessage("Finished job: " + currentJob.getTaskId());
				try {
					currentJobThread.join();
				} catch (InterruptedException e) {
					continue;
				}
				jobs.remove(currentJob);
				currentJob = null;
				currentJobThread = null;
			}
			
			//if there is currently not a job, then we need to start the next job
			if(currentJob == null && !jobs.isEmpty()) {
				currentJob = getNextJob();
				Console.writeMessage("Starting job: " + currentJob.getTaskId());
				
				//TODO: create a thread pool to reuse threads
				//we always kill the exisiting thread and start a new thread for each job, which may be inefficient
				currentJobThread = (new Thread(currentJob));
				currentJobThread.start();
			} 
			
		}
		Console.writeMessage("Scheduler stopped");
	}
	
	/**
	 * Gets the next job to be executed
	 */
	private Job getNextJob() {
		//we always sort the job on addition (in addJob) so the next job that 
		//needs to be executed is always at the 0-index
		return jobs.get(0);
	}
}
