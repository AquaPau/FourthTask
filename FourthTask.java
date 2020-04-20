import java.util.concurrent.Semaphore;

class Worker extends Thread{
    Semaphore part;
    Worker(String name, Semaphore table){
        super(name);
        this.part = table;
    }

    @Override
    public void run() {
        for (int i = 1; i < 4;i++) {
            try {
                part.acquire();
                System.out.println(this.getName() + " деталь " + i);
                part.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class FourthTask {
    public static void main(String[] args) throws Exception {
        Semaphore part = new Semaphore(1);
        Worker worker[] = new Worker[5];
        for (int i = 0; i < worker.length; i++) {
            worker[i] = new Worker("Рабочий" + (i + 1), part);
        }
        for (int i = 0; i < worker.length; i++) {
            worker[i].start();
        }
    }
}



