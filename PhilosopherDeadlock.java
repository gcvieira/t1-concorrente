public class PhilosopherDeadlock implements Runnable {

	// The forks on either side of this Philosopher 
	private Object leftFork;
	private Object rightFork;

	// Member variables, standard constructor
	public PhilosopherDeadlock(Object leftFork, Object rightFork) {
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
			int numeroIteracoes = 1;
			while (numeroIteracoes <= 5) {
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
					numeroIteracoes++;
				}
			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}

	public static void main(String[] args) throws Exception {
		final PhilosopherDeadlock[] philosophers = new PhilosopherDeadlock[5];
		Object[] forks = new Object[philosophers.length];

		for (int i = 0; i < forks.length; i++)
			forks[i] = new Object();

		for (int i = 0; i < philosophers.length; i++) {
			Object leftFork = forks[i];
			Object rightFork = forks[(i + 1) % forks.length];

			if (i == philosophers.length - 1) { 
				// The last philosopher picks up the right fork first
				philosophers[i] = new PhilosopherDeadlock(rightFork, leftFork);
			} else {
				philosophers[i] = new PhilosopherDeadlock(leftFork, rightFork);
			}

			Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
			t.start();
		}

	}
}
