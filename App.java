import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

//класс работник
class Worker implements Runnable {
    private Conveyer conveyer;
    Semaphore sem;

    Worker(Conveyer conveyer, Semaphore sem){
        this.conveyer = conveyer;
        this.sem = sem;
    }
    //работник получает деталь с конвеера и обрабатывает ее
    public void run() {
            synchronized (this) {
                try {
                    sem.acquire();
                }catch (Exception e){}
                System.out.println(Thread.currentThread().getName() +" -  "+ conveyer.getPart());
                conveyer.RefactorPart();
                sem.release();
        }
    }
}

class Conveyer{
    private static AtomicInteger part = new AtomicInteger(1);
    private static int counter =0;

    public synchronized AtomicInteger getPart(){
        counter++;
        return part;
    }

    public void RefactorPart(){
        synchronized (this){
            if (counter == 5){ //потому что 5 работников
                part.incrementAndGet();
                counter =0;
            }

        }
    }
}

public class App {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);//5 работников например
        Conveyer conveyer = new Conveyer(); //класс конвеер откуда работники берут детальки
        Semaphore sem = new Semaphore(1); //семафор нужен для того чтобы рабочие производили детали поочередно


        for (int j =0; j < 3; j++) { //Нужно произвести три детали например
            for (int i = 0; i < 5; i++) { // 5 рабочих к работе приступают
                executor.submit(new Worker(conveyer, sem));
            }
        }
        executor.shutdown();

    }
}

