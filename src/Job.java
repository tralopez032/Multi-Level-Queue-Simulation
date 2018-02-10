
public class Job {

	private int pid;				//used to identify job
	private int arrivalTime;		//time it will enter the system
	private int cpuTimeRequired;	//amount of cpu time the job needs
	private int cpuTimeRemaining;	//amount of cpu time left until job completes
	private int currentQueue;		//level of the queue that the job is currently in

	/**
	 * Constructor - initializes global variables
	 */
	public Job(){
		arrivalTime = 0;
		pid = 0;
		cpuTimeRequired = 0;
		cpuTimeRemaining = 0;
	}
	
	/**
	 * Constructor - initializes global variables
	 * @param arriveTime - time of arrival
	 * @param id - pid of the job
	 * @param cpuTime - the amount of cpu time required by the job
	 */
	public Job(int arriveTime, int id, int cpuTime) {
		arrivalTime = arriveTime;
		pid = id;
		cpuTimeRequired = cpuTime;
		cpuTimeRemaining = cpuTimeRequired;
	}
	
	
	/**
	 * Decrements the time remaining until the job completes
	 */
	public void decrementTimeRemaining(){
		cpuTimeRemaining--;
	}

	
	/**
	 * Sets the current queue of the job
	 * @param q - level of queue
	 */
	public void setCurrentQueue(int q){
		currentQueue = q;
	}
	
	
	/**
	 * Returns the cpu time remaining until the job is completed
	 * @return cpuTimeRemaining
	 */
	public int getCpuTimeRemaining(){
		return cpuTimeRemaining;
	}
	
	
	/**
	 * Returns the total cpu time required by the job 
	 * @return cpuTimeRequired
	 */
	public int getCpuTimeRequired(){
		return cpuTimeRequired;
	}
	
	
	/**
	 * Returns the location of the current queue of the job
	 * @return currentQueue
	 */
	public int getCurrentQueue(){
		return currentQueue;
	}
	
	
	/**
	 * Returns arrival time of the job
	 * @return arrivalTime
	 */
	public int getArrivalTime(){
		return arrivalTime;
	}
	
	
	/**
	 * Returns pid of the job
	 * @return pid
	 */
	public int getPid(){
		return pid;
	}
}
