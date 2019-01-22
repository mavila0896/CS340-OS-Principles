
public class Assignment2 {

	public static void main(String[] args) throws InterruptedException {
		Monitor pM = new Monitor();
		Philosopher[] dPhil = new Philosopher[5];

		for (int i = 0; i < dPhil.length; i++) {
			dPhil[i] = new Philosopher(i, pM);
		}

		for (int i = 0; i < dPhil.length; i++) {
			dPhil[i].start();
		}

		for (int i = 0; i < dPhil.length; i++) {
			dPhil[i].join();
		}

	}

}
