public class PhilosopherConcurrent implements Runnable {

	// The forks on either side of this Philosopher 
	private Object leftFork;
	private Object rightFork;

	// Member variables, standard constructor
	public PhilosopherConcurrent (Object leftFork, Object rightFork) {
		this.leftFork = leftFork;
		this.rightFork = rightFork;
	}

	private void doAction(String action) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + action);
		Thread.sleep(( (int) (Math.random() * 100) ));
	}


	@Override
	public void run() {
		try {
			while (true) {
				// thinking
				doAction(System.nanoTime() + ": Thinking");

				synchronized (leftFork) { 
					doAction( System.nanoTime() + ": Picked up left fork");
					synchronized (rightFork) {
						// eating
						doAction( System.nanoTime() + ": Picked up right fork - eating");
						doAction( System.nanoTime() + ": Put down right fork");
					}

					// Back to thinking
					doAction( System.nanoTime() + ": Put down left fork. Back to thinking");
				}
			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}

	public static void main(String[] args) throws Exception {
		PhilosopherConcurrent[] philosophers = new PhilosopherConcurrent[5];
		Object[] forks = new Object[philosophers.length];

		for (int i = 0; i < forks.length; i++)
			forks[i] = new Object();

		for (int i = 0; i < philosophers.length; i++) {
			Object leftFork = forks[i];
			Object rightFork = forks[(i + 1) % forks.length];

			philosophers[i] = new PhilosopherConcurrent(leftFork, rightFork);

			Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
			t.start();
		}

	}
}
