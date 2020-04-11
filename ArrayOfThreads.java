import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//Класс рабочий
class Worker extends Thread {
    Conveyer conveyer;
    int numberOfDetails;

    Worker(String name, Conveyer conveyer, int numberOfDetails) {
        super(name);
        this.conveyer = conveyer;
        this.numberOfDetails = numberOfDetails;
    }
    public void run() {
        //Если деталей на конвеере нет переходим в else где первый рабочий создает детали и ложит их на конвеер

        //Когда на конвеер первый рабочий положил детали, то другие рабочии начинают эти детали обрабатывать и ложить
        //на конвеер обратно
        if (!conveyer.detailsOnConveyer.isEmpty()) {
            String gottenDetail = conveyer.GetFromConveyer();
            System.out.println(Thread.currentThread().getName() +"- "+ gottenDetail);
            String RefactoredDetail = gottenDetail;
            conveyer.InsertIntoConveyer(RefactoredDetail);
        } else {
            MakeDetail makeDetail = new MakeDetail();
            for (int i =0; i < numberOfDetails; i++) {
                String nameOfDetail = makeDetail.DetailisMade();
                conveyer.PushToConveyer(nameOfDetail);
                System.out.println(Thread.currentThread().getName() + "- " + nameOfDetail);
            }
        }
    }
}
//класс в котором первый рабочий создает свои детали
class MakeDetail{
    private static int detailNmber = 0;
    public synchronized String DetailisMade(){
        detailNmber++;
        return "деталь" + detailNmber;
    }
}
//Конвеер
class Conveyer{
    //Массив ArrayList является конвеером
    public static final List<String> detailsOnConveyer=new ArrayList<String>();

    //Метод который ложит детали на конвеер (сначала деталь1, деталь2, деталь3)
    public synchronized void PushToConveyer(String detail){
        detailsOnConveyer.add(detail);

    }
    //метод забора с конвеера который позволякт забирать деталь лежащей первой на конвеере
    public synchronized String GetFromConveyer(){
        String rem_Element = detailsOnConveyer.get(0);
        detailsOnConveyer.remove(0);
        return rem_Element;
    }

    //Метод который позволяет положить деталь на первое место на конвеере
    public synchronized void InsertIntoConveyer(String detail){
        detailsOnConveyer.add(0, detail);
    }
}

public class ArrayOfThreads{
    public static void main(String args[])
    {
        //Задаю количество рабочих, например 5
        Worker worker[] = new Worker[5];
        //Запускаю конвеер
        Conveyer conveyer = new Conveyer();
        //Задаю количество деталей
        int numberOfDetails = 3;

        //Создаю 5 классов потоков с именем рабочий 1,2,3,4,5
        //передаю туда свой конвеер и количество деталей мне нужных
        for(int i = 0; i < worker.length; i++)
        {
            worker[i] = new Worker("Рабочий" + (i+1), conveyer, numberOfDetails);
        }

        //Запускаю все потоки-рабочих
        for (int i =0; i < worker.length; i++) {
            worker[i].start();
            try {
                //без sleep сразу все начинает ломаться, я не знаю как его заменить на что то другое
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){}
        }


    }
}