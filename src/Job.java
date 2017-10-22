
public class Job {

	private int pid;
	private int arrivalTime;
	private int cpuTimeRequired;
	private int cpuTimeRemaining;
	private int currentQueue;

	
	public Job(){
		arrivalTime = 0;
		pid = 0;
		cpuTimeRequired = 0;
		cpuTimeRemaining = 0;
	}
	
	public Job(int arriveTime, int id, int cpuTime) {
		arrivalTime = arriveTime;
		pid = id;
		cpuTimeRequired = cpuTime;
		cpuTimeRemaining = cpuTimeRequired;
	}
	
	
	/**
	 * 
	 */
	public void decrementTimeRemaining(){
		cpuTimeRemaining--;
	}

	
	/**
	 * 
	 * @param q
	 */
	public void setCurrentQueue(int q){
		currentQueue = q;
	}
	
	
	/**
	 * returns the cpu time remaining until the job is completed
	 * @return cpuTimeRemaining
	 */
	public int getCpuTimeRemaining(){
		return cpuTimeRemaining;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getCpuTimeRequired(){
		return cpuTimeRequired;
	}
	
	
	/**
	 * returns the location of the current queue of the job
	 * @return currentQueue
	 */
	public int getCurrentQueue(){
		return currentQueue;
	}
	
	
	/**
	 * 
	 * @return arrivalTime
	 */
	public int getArrivalTime(){
		return arrivalTime;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getPid(){
		return pid;
	}
}
