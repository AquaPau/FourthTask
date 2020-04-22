import java.util.Random;

public class Task implements Runnable {
    private static final Random random = new Random(1000);
    private final int number;
    private State state;
    private final int delay;

    protected Task(int number) {
        this.state = State.NEW;
        this.number = number;
        this.delay = random.nextInt(50000);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + ": " + toString());
        state = state.progressState();
    }

    @Override
    public String toString() {
        return "Task{" +
                "number=" + number +
                ", delay=" + delay +
                '}';
    }
}
