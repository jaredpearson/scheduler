package scheduler2;

import java.util.Calendar;

/**
 * Mock task that performs an operation for the given number of milliseconds. 
 * If the task is interrupted and restarted, it will continue on at the place it
 * left off.
 * @author Jared Pearson
 */
class MockTask implements Task {
	private final int id;
	private int operationLeft = 5000;
	
	public MockTask(int id) {
		this.id = id;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public boolean isFinished() {
		return this.operationLeft == 0;
	}
	
	@Override
	public void exec() {

		//simulate a long running operation by sleeping a specified amount.
		//if we are interrupted, then we need to store rest of the duration so we can continue later
		Console.writeMessage("Job will take " + operationLeft + " millis");
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		try {
			Thread.sleep(this.operationLeft);
			this.operationLeft = 0;
		} catch (InterruptedException e) {
			long interruptedTime = Calendar.getInstance().getTimeInMillis();
			long duration = interruptedTime - startTime;
			this.operationLeft -= duration;
			Console.writeMessage("Job interrupted " + duration + " millis in. Need " + operationLeft + " more millis to complete");
		}
	}
}