import java.util.concurrent.LinkedBlockingQueue;

public class TaskQuery extends LinkedBlockingQueue<Task> {

    public static TaskQuery create(int numberOfTasks) {
        TaskQuery result = new TaskQuery();
        for (int i = 1; i <= numberOfTasks; i++) {
            Task newTask = new Task(i);
            result.add(newTask);
        }
        return result;
    }
}
