import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor implements Dine {

	final int NUM_P = 5;
	Philosopher[] phils;
	private ReentrantLock pLock;
	private Condition philS[];

	@Override
	public void takeChopsticks(int pNum) {
		pLock.lock();
		phils[pNum].setPState(Philosopher.STATE.HUNGRY);
		test(pNum);
		if (phils[pNum].getPState() != Philosopher.STATE.EATING) {
			try {
				philS[pNum].await();
			} catch (InterruptedException e) {
				System.out.println("Failed to get philosopher " + pNum + " to wait.");
				e.printStackTrace();
			}
		}
		pLock.unlock();
	}

	@Override
	public void returnChopsticks(int pNumber) {
		pLock.lock();
		System.out.println("P " + pNumber + " released left & right chopsticks.");
		phils[pNumber].setPState(Philosopher.STATE.THINKING);
		System.out.println("P " + pNumber + " is thinking.");
		test((pNumber + 4) % 5);
		test((pNumber + 1) % 5);
		pLock.unlock();

		try {
			phils[pNumber].sleep(getRandomSleepTime());
		} catch (Exception e) {
			System.out.println("Error:  " + pNumber + " can't go to sleep.");
		}
	}

	public void dineP(int p) {
		takeChopsticks(p);
		returnChopsticks(p);
	}

	final int MAX_SLEEP = 4000;

	private int getRandomSleepTime() {
		return (int) (Math.random() * MAX_SLEEP);
	}

	public void test(int philNum) {
		if ((phils[(philNum + 4) % 5].getPState() != Philosopher.STATE.EATING)
				&& (phils[(philNum + 1) % 5].getPState() != Philosopher.STATE.EATING)
				&& (phils[philNum].getPState() == Philosopher.STATE.HUNGRY)) {

			System.out.println("P " + philNum + " picks up left & right chopsticks.");
			phils[philNum].setPState(Philosopher.STATE.EATING);

			System.out.println("P " + philNum + " is eating.");

			philS[philNum].signal();

			try {
				phils[philNum].sleep(getRandomSleepTime());
			} catch (Exception e) {
				System.out.println("Error: " + philNum + " can't go to sleep.");
			}
		}
	}
	public Monitor() {
		phils = new Philosopher[NUM_P];
		philS = new Condition[NUM_P];
		pLock = new ReentrantLock();
		for (int i = 0; i < NUM_P; i++) {
			phils[i] = new Philosopher(i, this);
			philS[i] = pLock.newCondition();
		}
	}

}