/**
 * Draws the visual simulation
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VisualRepresentation extends JPanel{

	private JFrame frame;
	private Clock visualClock;
	
	//Rectangles representing queues and cpu
	private Rectangle2D.Double queue1;
	private Rectangle2D.Double queue2;
	private Rectangle2D.Double queue3;
	private Rectangle2D.Double queue4;
	private Rectangle2D.Double cpu;
	
	//used to determine how far into the queue the job can go
	private int q1MaxX;
	private int q2MaxX;
	private int q3MaxX;
	private int q4MaxX;
	
	private JobVisual jv [];		//array of rectangles representing jobs
	private Font font;				//font of the strings drawn
	private int indexOfJobInCpu;	//index of the rectangle in jv[] that represents the job currently in cpu 
	
	/**
	 * Constructor - initialized global variables
	 */
	public VisualRepresentation() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 700);
		frame.setResizable(false);
		frame.setTitle("Multi-level Queue Simulation");
		visualClock = new Clock();
		queue1 = new Rectangle2D.Double();
		queue2 = new Rectangle2D.Double();
		queue3 = new Rectangle2D.Double();
		queue4 = new Rectangle2D.Double();
		cpu = new Rectangle2D.Double();
		q1MaxX = 0;
		q2MaxX = 0;
		q3MaxX = 0;
		q4MaxX = 0;
		jv = new JobVisual[16];
		font = new Font("TimesRoman", Font.PLAIN, 30);
		indexOfJobInCpu = 0;
		}

	
	/**
	 * Draws graphics on screen
	 */
	public void displaySimulation(){
		queue1.setFrame(100, 100, 200, 60);
		queue2.setFrame(100, 220, 200, 60);
		queue3.setFrame(100, 340, 200, 60);
		queue4.setFrame(100, 460, 200, 60);
		cpu.setFrame(350, 100, 100, 430);
		q1MaxX = (int) queue1.getMaxX();
		q2MaxX = (int) queue2.getMaxX();
		q3MaxX = (int) queue3.getMaxX();
		q4MaxX = (int) queue4.getMaxX();
		
		for(int i = 0; i <16; i++){
			jv[i] = new JobVisual();
		}
	
		frame.add(new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;				

				g2.drawString("Level 1", 100, 95);
				g2.draw(queue1);
				g2.drawString("Level 2", 100, 215);
				g2.draw(queue2);
				g2.drawString("Level 3", 100, 335);
				g2.draw(queue3);
				g2.drawString("Level 4", 100, 455);
				g2.draw(queue4);
				g2.drawString("CPU", 350, 95);
				g2.draw(cpu);
				
				for(int i = 0; i < 16; i++){
					if(jv[i].getJobRep().getX() > cpu.getX() && jv[i].getJobRep().getMaxX()<cpu.getMaxX()){
						g2.setColor(Color.green);
						g2.draw(cpu);
						g2.fill(cpu);
					}else if(jv[i].getJobRep().getX() > cpu.getMaxX()&& jv[i].getJobRep().getX() < 600){
						g2.setColor(Color.GRAY);
						g2.drawString("Job Complete! ", 480, 310);
					}
					jv[i].paint(g2);
				}
				g2.setFont(font);
				g2.drawString("System Time: " + Integer.toString(visualClock.getTime()), 20, 40);
			}
		});
	}
	
	/**
	 * Updates the clock on screen and the time left for the job on the cpu
	 */
	public void updateVisualClock(){
		visualClock.tick();
		if(indexOfJobInCpu >= 0){
			jv[indexOfJobInCpu].updateTime();
		}
		frame.repaint();
	}
	
	/**
	 * Inserts new rectangle into visual simulation and moves it right into the first queue
	 * @param pid - id of the rectangle to move
	 * @param timeNeeded - cpu time job requires 
	 */
	public void addJob(int pid, int timeNeeded){
		jv[pid-101].setString(Integer.toString(pid), timeNeeded);
		moveDown(pid);
		moveRight(pid, q1MaxX);
		frame.repaint();
		q1MaxX = (int) jv[pid-101].getJobRep().getX();
	}
		
	/**
	 * Moves rectangle right until its inside cpu
	 * @param pid - id of rectangle to move
	 * @param queue - queue that the rectangle came from
	 */
	public void enterCpu(int pid, int queue){
		moveRight(pid, (int) cpu.getMaxX()-30);
		indexOfJobInCpu = pid-101;
		
		for(int i = 0; i<16; i++){
			if(i != pid-101){
				if(jv[i].getJobRep().getY() == jv[pid-101].getJobRep().getY()){
					moveRight(i+101, (int)jv[i].getJobRep().getMaxX()+50);
				}
			}
		}
		
		if(queue == 1){
			q1MaxX += 50;
		}else if(queue == 2){
			q2MaxX += 50;
		}else if(queue == 3){
			q3MaxX += 50;
		}else if(queue == 4){
			q4MaxX += 50;
		}
	}
	
	
	/**
	 * Moves rectangle into the next level queue
	 * @param pid - id of the rectangle to move
	 * @param lastQueue - level of the last queue that the rectangle was in
	 */
	public void moveJobToLowerQueue(int pid, int lastQueue){
		moveDown(pid);
		
		//moves left
		Thread animationThread3 = new Thread(new Runnable(){
			public void run(){
				while(jv[pid-101].getJobRep().getMinX() > 100){
					jv[pid - 101].getJobRep().x = (jv[pid-101].getJobRep().getX() - 10);
					jv[pid-101].getJobRep().setFrame(jv[pid-101].getJobRep());
					frame.repaint();
				}
				try{Thread.sleep(100);} catch(Exception ex){}
			}
		});
		animationThread3.start();
		while(animationThread3.getState() != Thread.State.TERMINATED){	//waits until its done moving to continue
		}
		
		if(lastQueue < 4){
			moveDown(pid);
		}else{
			moveUp(pid);
		}
		
		//insert into new queue
		if(lastQueue == 1){
			moveRight(pid, q2MaxX);
			q2MaxX = (int) jv[pid-101].getJobRep().getX();
		}
		else if(lastQueue == 2){
			moveRight(pid, q3MaxX);
			q3MaxX = (int) jv[pid-101].getJobRep().getX();
		}
		else if(lastQueue >= 3){
			moveRight(pid, q4MaxX);
			q4MaxX = (int) jv[pid-101].getJobRep().getX();
		}
		
		indexOfJobInCpu = -1;
	}
	
	
	/**
	 * Moves rectangle off of screen
	 * @param pid - id of rectangle to move
	 */
	public void removeJob(int pid){
		moveRight(pid, 700);
	}

	
	/**
	 * Moves rectangle right until it reaches a certain destination
	 * @param pid - id of the rectangle to move
	 * @param destination - new location of the rectangle after moving right
	 */
	public void moveRight(int pid, int destination){
		Thread animationThread1 = new Thread(new Runnable(){
			public void run(){
				while(jv[pid-101].getJobRep().getMaxX() < destination){
					jv[pid-101].getJobRep().x = jv[pid-101].getJobRep().getX() + 10;
					jv[pid-101].getJobRep().setFrame(jv[pid-101].getJobRep());
					frame.repaint();
					try{Thread.sleep(100);} catch(Exception ex){}
				}
			}
		});
		animationThread1.start();
		while(animationThread1.getState() != Thread.State.TERMINATED){	//waits until its done moving to continue
		}
	}
	
	
	/**
	 * Moves rectangle down 60
	 * @param pid - pid of the rectangle to move
	 */
	public void moveDown(int pid){
		int yMax = (int) jv[pid-101].getJobRep().getY() + 60;
		
		Thread animationThread2 = new Thread(new Runnable(){
			public void run(){
				while(jv[pid-101].getJobRep().y < yMax){
					jv[pid-101].getJobRep().y = (jv[pid-101].getJobRep().getY() + 10);
					jv[pid-101].getJobRep().setFrame(jv[pid-101].getJobRep());
					frame.repaint();
				}
				try{Thread.sleep(100);} catch(Exception ex){}
			}
		});
		animationThread2.start();
		while(animationThread2.getState() != Thread.State.TERMINATED){	//waits until its done moving to continue
		}
	}

	
	/**
	 * Moves rectangle object up
	 * @param pid - id of the rectangle to move
	 */
	public void moveUp(int pid){
		Thread animationThread4 = new Thread(new Runnable(){
			public void run(){
				while(jv[pid-101].getJobRep().y > 470){
					jv[pid-101].getJobRep().y = (jv[pid-101].getJobRep().getY() - 10);
					jv[pid-101].getJobRep().setFrame(jv[pid-101].getJobRep());
					frame.repaint();
				}
				try{Thread.sleep(100);} catch(Exception ex){}
			}
		});
		animationThread4.start();
		while(animationThread4.getState() != Thread.State.TERMINATED){ //waits until its done moving to continue
		}
	}
}
