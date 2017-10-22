
public class Clock {

	int time;
	
	public Clock() {
		time = 0;
	}

	public void tick(){
		time++;
	}
	
	public int getTime(){
		return time;
	}
}
