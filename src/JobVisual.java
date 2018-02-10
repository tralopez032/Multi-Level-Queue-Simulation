/**
 * draws a visual representation of a job with a pid for identification and a time representing the amount of cpu time a jobs until it is complete
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class JobVisual {

	private Rectangle2D.Double job;			//rectangle representing a job on screen
	private String pid;						//pid of the job - used to identify jobs on screen
	private int time;						//cpu time the job needs until completion
	private Font font;						//font of the string drawn on screen
	
	
	/**
	 * Constructor - initializes global variables
	 */
	public JobVisual() {
		job = new Rectangle2D.Double(-50, 45, 50, 50);
		pid = " ";
		time = 0;
		font = new Font("Baskerville", Font.BOLD, 15);
	}
	
	
	/**
	 * draws the graphic representing a job
	 * @param g - graphic used to draw
	 */
	public void paint(Graphics g){
		Graphics2D gg = (Graphics2D) g;
		

		if(job.getX() > 400){
			gg.setColor(Color.GRAY);
		}else{
			gg.setColor(Color.BLUE);
		}
		gg.draw(job);
		gg.fill(job);
	
		gg.setColor(Color.BLACK);
	
		gg.drawString(pid, (int)job.getX()+15, (int)job.getY()+15);
		gg.setFont(font);
		gg.drawString(Integer.toString(time), (int)job.getX()+21, (int)job.getY()+30);
	}

	 
	/**
	 * sets the strings that will be drawn on the screen
	 * @param pid - pid of the job
	 * @param time - the amount of cpu time the job needs
	 */
	public void setString(String pid, int time){
		this.pid = pid;
		this.time = time;
	}
	
	
	/**
	 * updates the cpu time required for job until completion on screen
	 */
	public void updateTime(){
		time --;
	}
	
	
	/**
	 * returns a job representation
	 * @return job - the rectangle that represents a job
	 */
	public Rectangle2D.Double getJobRep(){
		return job;
	}
}
