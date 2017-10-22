import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class JobVisual {

	private Rectangle2D.Double job;
	private Graphics g1;
	private String pid;
	private int time;
	private Font font;
	
	
	public JobVisual() {
		job = new Rectangle2D.Double(-50, 45, 50, 50);
		pid = " ";
		time = 0;
		font = new Font("Baskerville", Font.BOLD, 15);
	}
	
	
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

	 
	public void setString(String pid, int time){
		this.pid = pid;
		this.time = time;
	}
	
	public void updateTime(){
		time --;
	}
	
	public Rectangle2D.Double getJobRep(){
		return job;
	}
}
