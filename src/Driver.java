/**
 * Project Title: Multi-Level Feedback Queue Simulation (Lab3)
 * Project Description: simulates the inner workings of a computer system with multiple jobs and only one cpu
 * 						illustrates how the jobs are managed and completed
 * Date: October 22, 2017
 * Palomar ID: 010654446
 * User Instructions: provide input file containing job (mfq.txt), run project
 * @author Tracey Lopez
 */

import java.io.*;

public class Driver {
	
	public static void main(String[] args) throws IOException, InterruptedException{
	
		PrintWriter pw = new PrintWriter(new FileWriter("csis.txt"));
		MFQ mfq = new MFQ(pw);
		mfq.getJobs();
		mfq.outputHeader();
		mfq.runSimulation();
		mfq.outStats();
		pw.close();
	}
}
