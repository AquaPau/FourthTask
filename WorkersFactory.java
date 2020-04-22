import java.util.concurrent.ThreadFactory;

public class WorkersFactory implements ThreadFactory {
    private int workerNumber;

    public Thread newThread(Runnable r) {
        workerNumber++;
        return new Thread(r, "Worker " + workerNumber);
    }

}
