
public class Philosopher extends Thread {
	int pNum;
	Monitor pMon;
	STATE state;

	public Philosopher(int philNum, Monitor pMonitor) {
		this.state = STATE.THINKING;
		this.pNum = philNum;
		this.pMon = pMonitor;
	}

	public enum STATE {
		THINKING, 
		HUNGRY, 
		EATING
	};

	@Override
	public void run() {
		while (true) {
			pMon.dineP(pNum);
		}
	}

	public STATE getPState() {
		return state;
	}

	public void setPState(STATE state) {
		this.state = state;
	}
}