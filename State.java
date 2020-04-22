//Was added only to show it is possible to check states of tasks avoiding futures or completableFutures
public enum State {
    NEW, DONE;

    public State progressState() {
        if (this == NEW) {
            return DONE;
        } else {
            throw new UnsupportedOperationException("Task has been already done");
        }
    }
}
