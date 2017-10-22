
public class CPU {
	private Job job;				//holds current job on cpu
	private int cpuQuantumClock;	//will reset every time it starts a new process(receives a new job)
	private boolean busyFlag;		//whether or not there is a job on the cpu
	
	public CPU() {
		job = new Job();
		cpuQuantumClock = 0;
		busyFlag = false;
	}

	
	/**
	 * 
	 * @param j1
	 * @param queue
	 */
	public void setJob(Job j1, int queue){
		job = j1;
		cpuQuantumClock = (int) Math.pow(2, queue);		//set the appropriate time base on the queue level
		busyFlag = true;
	}
	
	
	/**
	 * 
	 * @param b
	 */
	public void setBusyFlag(boolean b){
		busyFlag = b;
	}
	
	
	/**
	 * 
	 */
	public void tick(){
		if(cpuQuantumClock > 0){
			cpuQuantumClock --;
		}
		job.decrementTimeRemaining();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Job getJob(){
		return job;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getCpuQuantumClock(){
		return cpuQuantumClock;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean busyFlag(){
		return busyFlag;
	}
}
