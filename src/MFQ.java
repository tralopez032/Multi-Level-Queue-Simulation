/**
 * Reads input from mfg.txt to create jobs and processes them
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class MFQ {
	private PrintWriter pw;			//writes to csis.txt
	private Scanner in;
	private ObjectQueue jobs;		//holds all jobs
	
	//queues
	private ObjectQueue level1;
	private ObjectQueue level2;
	private ObjectQueue level3;
	private ObjectQueue level4;
	
	private Clock systemClock;
	private CPU cpu;
	private int totalJobs;				//total number of jobs
	private int totalTime;				//total time that the system runs for
	private int totalWaitTime;			//total wait time of all jobs
	private int cpuIdleTime;			//total time the cpu is idle for
	private int totalResponseTime;		//total response time of all jobs 
	private VisualRepresentation vr;	
	
	
	/**
	 * Constructor - initialized global variables
	 * @param p - output file
	 * @throws FileNotFoundException
	 */
	public MFQ(PrintWriter p) throws FileNotFoundException {
		pw = p;
		in = new Scanner(new File("mfq.txt"));
		jobs = new ObjectQueue();
		level1 = new ObjectQueue();
		level2 = new ObjectQueue();
		level3 = new ObjectQueue();
		level4 = new ObjectQueue();
		systemClock = new Clock();
		cpu = new CPU();
		totalJobs = 0;
		totalTime = 0;
		totalWaitTime = 0;
		cpuIdleTime = 0;
		vr = new VisualRepresentation();
	}

	
	
	/**
	 * Reads input from mfq.txt and uses input to instantiate all jobs
	 */
	public void getJobs(){
		String input = "/0";
		String delims = "[ ]+";
		String[] tokens;
		
		while(in.hasNextLine()){
			input = in.nextLine();
			tokens = input.split(delims);
			jobs.insert(new Job(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
			totalJobs++;
		}
	}

	
	
	/**
	 * Prints header to output file csis.txt
	 */
	public void outputHeader(){
		pw.println("Event\t\tSystem Time\tProcess ID\tCPU Time Required\tTotal Time in System\tLowest Level Queue");
		pw.println("_________________________________________________________________________________________________________________");
	}
	
	
	
	/**
	 * Runs the simulation
	 * Manages and processes all jobs
	 */
	public void runSimulation(){
		Job nextJob = new Job();

		nextJob = (Job) jobs.remove();
		vr.displaySimulation();
	
		//continue to loop until all jobs are complete (all queues are empty and cpu is not busy)
		while(!jobs.isEmpty()||cpu.busyFlag()|| !level1.isEmpty()|| !level2.isEmpty()|| !level3.isEmpty()|| !level4.isEmpty()){
			systemClock.tick();
			vr.updateVisualClock();
			cpu.tick();
			
			
			//if there is a job currently using cpu, check if it is finished or cpu processing time is done
			if(cpu.busyFlag()){
				if(cpu.getJob().getCpuTimeRemaining() == 0){
					totalTime += (systemClock.getTime()-cpu.getJob().getArrivalTime());
					totalWaitTime += (systemClock.getTime()-cpu.getJob().getArrivalTime()) - cpu.getJob().getCpuTimeRequired();
					pw.println("Departure\t     " + systemClock.getTime() + "   \t   " + cpu.getJob().getPid() + "\t\t\t\t\t\t   " + (systemClock.getTime()-cpu.getJob().getArrivalTime()) + "\t\t\t" + cpu.getJob().getCurrentQueue());
					vr.removeJob(cpu.getJob().getPid());
					cpu.setBusyFlag(false);
				}else if(cpu.getCpuQuantumClock() == 0){	//if the cpu process time is over and the job is still not done, insert it into the next lower level queue
					vr.moveJobToLowerQueue(cpu.getJob().getPid(), cpu.getJob().getCurrentQueue());
					moveJobToLowerQueue(cpu.getJob());
					cpu.setBusyFlag(false);
				}
			}
			
			//if it time for a new job to enter the system
			if(nextJob.getArrivalTime() == systemClock.getTime()){ //if it's time for the new job to enter, insert it into the first queue and assign to cpu
				level1.insert(nextJob);
				vr.addJob(nextJob.getPid(), nextJob.getCpuTimeRequired());
				nextJob.setCurrentQueue(1);
				if(cpu.busyFlag()){
					vr.moveJobToLowerQueue(cpu.getJob().getPid(), cpu.getJob().getCurrentQueue());
					moveJobToLowerQueue(cpu.getJob());		//since a new job has entered the system, any job using cpu must stop and move to lower level queue
				}
				cpu.setJob((Job)level1.remove(), 1);			//assign job to cpu
				vr.enterCpu(cpu.getJob().getPid(), cpu.getJob().getCurrentQueue());
				totalResponseTime = systemClock.getTime() - cpu.getJob().getArrivalTime();
				
				//output arrival message
				pw.println("Arrival\t\t     " + systemClock.getTime() + "\t\t   " + nextJob.getPid() + "\t\t\t" + nextJob.getCpuTimeRequired());
				
				//get the next job that you will be monitoring until it is time for it to enter the system
				if(!jobs.isEmpty()){
					nextJob = (Job) jobs.remove();
				}
			}
			
			//if there is no job currently using cpu
			if(cpu.busyFlag() == false){
				cpuIdleTime++;			//cpu is not being used so add to cpu idle time
				
				//find highest priority job in system to assign to cpu
				if(!level1.isEmpty()){
					cpu.setJob((Job)level1.query(), ((Job)level1.remove()).getCurrentQueue());
					vr.enterCpu(cpu.getJob().getPid(), cpu.getJob().getCurrentQueue());
					cpu.setBusyFlag(true);
				}else if(!level2.isEmpty()){
					cpu.setJob((Job)level2.query(), ((Job)level2.remove()).getCurrentQueue());
					vr.enterCpu(cpu.getJob().getPid(), cpu.getJob().getCurrentQueue());
					cpu.setBusyFlag(true);
				}else if(!level3.isEmpty()){
					cpu.setJob((Job)level3.query(), ((Job)level3.remove()).getCurrentQueue());
					vr.enterCpu(cpu.getJob().getPid(), cpu.getJob().getCurrentQueue());
					cpu.setBusyFlag(true);
				}else if(!level4.isEmpty()){
					cpu.setJob((Job)level4.query(), ((Job)level4.remove()).getCurrentQueue());
					vr.enterCpu(cpu.getJob().getPid(), cpu.getJob().getCurrentQueue());
					cpu.setBusyFlag(true);
				}
			}
		}
	}
	
	
	
	/**
	 * Moves the given job down the next lower level queue
	 * @param job - job that will be moved down to the next queue
	 */  			
	private void moveJobToLowerQueue(Job job){
		int currentQueue = job.getCurrentQueue();
		
		if(currentQueue == 1){
			level2.insert(job);
			job.setCurrentQueue(2);
		}else if(currentQueue == 2){
			level3.insert(job);
			job.setCurrentQueue(3);
		}else if(currentQueue>= 3){
			level4.insert(job);
			job.setCurrentQueue(4);
		}
	}
	
	
	
	/**
	 * Prints program statistics to output file csis.txt
	 */
	public void outStats(){
		double averageTurnaroundTime = (double)totalTime/totalJobs;
		double averageWaitTime = (double)totalWaitTime/totalJobs;
		
		pw.println();
		pw.println("Program Stats: ");
		pw.println("---------------");
		pw.println("Total number of Jobs: " + totalJobs);
		pw.println("Total time of all jobs in system: " + totalTime);
		pw.println("Average response time: " + String.format("%.2f", (double)totalResponseTime/totalJobs));
		pw.println("Average turnaround time for the jobs: " + String.format("%.2f", averageTurnaroundTime));
		pw.println("Average waiting time: " + String.format("%.2f", averageWaitTime));
		pw.println("Average throughput for the system as a whole: " + String.format("%.2f", (double)totalJobs/totalTime));
		pw.println("Total CPU idle time: " + cpuIdleTime);
	}
}
