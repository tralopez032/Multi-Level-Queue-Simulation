/**
 * Represents the cpu and processes jobs (one at a time)
 *
 */
public class CPU {
	private Job job;				//holds current job on cpu
	private int cpuQuantumClock;	//will reset every time cpu receives a new job
	private boolean busyFlag;		//whether or not there is a job on the cpu
	
	
	/**
	 * Constructor - initializes global variables
	 */
	public CPU() {
		job = new Job();
		cpuQuantumClock = 0;
		busyFlag = false;
	}

	
	/**
	 * Adds job to cpu and sets cpuQuantumClock
	 * @param j1 - job entering the cpu
	 * @param queue - the queue that the job was last in (used to set cpuQuantumClock to the appropriate time)
	 */
	public void setJob(Job j1, int queue){
		job = j1;
		cpuQuantumClock = (int) Math.pow(2, queue);		//set time base on the queue level
		busyFlag = true;
	}
	
	
	/**
	 * Modifies variable busyFlag
	 * @param b - boolean to set busyFlag to
	 */
	public void setBusyFlag(boolean b){
		busyFlag = b;
	}
	
	
	/**
	 * Decrements the cpuQuantumClock by 1
	 */
	public void tick(){
		if(cpuQuantumClock > 0){
			cpuQuantumClock --;
		}
		job.decrementTimeRemaining();
	}
	
	
	/**
	 * Returns job currently on cpu
	 * @return job - job on cpu
	 */
	public Job getJob(){
		return job;
	}
	
	
	/**
	 * Returns the current time left on cpuQuantumClock
	 * @return cpuQuantumClock
	 */
	public int getCpuQuantumClock(){
		return cpuQuantumClock;
	}
	
	
	/**
	 * Returns the status of the cpu
	 * @return busyFlag - true is there is a job on cpu, false if there is no job on cpu
	 */
	public boolean busyFlag(){
		return busyFlag;
	}
}
