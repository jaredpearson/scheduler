package scheduler2;

/**
 * Output console messages
 * @author Jared Pearson
 * TODO: replace with standard logging or other display
 */
public class Console {
	
	/**
	 * Writes a message to the console.
	 * @param message The message to be written
	 */
	public synchronized static void writeMessage(String message) {
		System.out.print("[" + Thread.currentThread().getId() + "] ");
		System.out.println(message);
	}

}
