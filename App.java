import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// As far only execution is performed in parallel, there is no need to configure anything except tasks
// As far we using thread-safe query we can avoid both synchronized or volatile usage.
public class App {
    private static int workersNumber = 2;
    private static int taskNumber = 10;
    private static final WorkersFactory workersFactory = new WorkersFactory();

    public static void main(String[] args) {
        if (args.length > 2) {
            workersNumber = Integer.parseInt(args[0]);
            taskNumber = Integer.parseInt(args[1]);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(workersNumber, workersFactory);
        TaskQuery tasks = TaskQuery.create(taskNumber);
        tasks.forEach(executorService::execute);
        executorService.shutdown();
    }
}

