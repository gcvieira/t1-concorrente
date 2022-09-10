public class PhilosopherLinear {

	// The forks on either side of this Philosopher 
	private Object leftFork;
	private Object rightFork;

	// Member variables, standard constructor
	public PhilosopherLinear (Object leftFork, Object rightFork) {
		this.leftFork = leftFork;
		this.rightFork = rightFork;
	}

	private static void doAction(String action) {
		System.out.println(action);
		try {
            Thread.sleep(( (int) (Math.random() * 100) ));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

	public static void run(PhilosopherLinear p) {
		int numeroIteracoes = 1;
		while (numeroIteracoes <= 5) {

			// thinking
			doAction( System.nanoTime() + ": Thinking ("+ p +")");

			// eating
			doAction( System.nanoTime() + ": Picked up left fork ("+ p +")");
			doAction( System.nanoTime() + ": Picked up right fork - eating ("+ p +")");

			// Back to thinking
			doAction( System.nanoTime() + ": Put down right fork ("+ p +")");
			doAction( System.nanoTime() + ": Put down left fork. Back to thinking ("+ p +")");

			numeroIteracoes++;
		}
	}

	public static void main(String[] args) {
		PhilosopherLinear [] philosophers = new PhilosopherLinear[5];
		Object[] forks = new Object[philosophers.length];

		for (int i = 0; i < forks.length; i++)
			forks[i] = new Object();

		for (int i = 0; i < philosophers.length; i++) {
			Object leftFork = forks[i];
			Object rightFork = forks[(i + 1) % forks.length];

			philosophers[i] = new PhilosopherLinear(leftFork, rightFork);

			System.out.println("\nPhilosopher "+ i +" "+ philosophers[i]);
			run( philosophers[i] );
		}
	}
}
