//Класс рабочий
class Worker extends Thread {
    int numberOfDetails;

    Worker(String name, int numberOfDetails) {
        super(name);
        this.numberOfDetails = numberOfDetails;
    }
    public void run() {
        synchronized (this) {
            for (int i = 0; i < numberOfDetails; i++) {
                System.out.println(Thread.currentThread().getName() + " -деталь" + (i + 1));
                notify();
            }
        }
    }
}

public class ArrayOfThreads{
    public static void main(String args[]) {
        if (args.length == 0) {
            System.err.println("Не введены параметры!");
            System.exit(0);
        }
        int numberOfThreads=0;
        int numberOfDetails=0;

        try{
            //Задаю количество рабочих
            numberOfThreads = Integer.parseInt(args[0]);
            //Задаю количество деталей
            numberOfDetails = Integer.parseInt(args[1]);
            if (numberOfThreads == 0 || numberOfDetails ==0){
                throw new IllegalArgumentException();
            }
        }catch (java.lang.NumberFormatException exc){
            System.out.println("Ввод не может быть словом " + exc);
            System.exit(0);
        }catch (IllegalArgumentException e){
            System.out.println("Вводимый параметр не может быть нулем " +e);
            System.exit(0);
        }

        //Массив принимающих потоки-рабочих
        Worker worker[] = new Worker[numberOfThreads];
        for(int i = 0; i < worker.length; i++) {
            try {
                worker[i] = new Worker("Рабочий" + (i + 1), numberOfDetails);
                worker[i].start();
                worker[0].join();
                worker[i].wait();
            }catch (Exception e){}
        }
    }
}