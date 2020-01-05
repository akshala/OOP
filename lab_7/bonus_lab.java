import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Fibonacci{
    private int num, answer = 0;

    Fibonacci(int num){
        this.num = num;
    }

//    FLYWEIGHT
    private static Map<Integer, Fibonacci> fibonacci_values = new HashMap<>();

    private static Fibonacci get_fibonacci_values(int n){
        if(!fibonacci_values.containsKey(n)){
            fibonacci_values.put(n, new Fibonacci(n));
        }
        return fibonacci_values.get(n);
    }

    int get_fibonacci(int num){
        if(num < 2){
            return num;
        }
        else{
            Fibonacci fib1 = get_fibonacci_values(num-1);
            Fibonacci fib2 = get_fibonacci_values(num-2);
            int ans1 = fib1.get_answer();
            int ans2 = fib2.get_answer();
            if(ans1 == 0){
                ans1 = fib1.get_fibonacci(num - 1);
            }
            if(ans2 == 0){
                ans2 = fib2.get_fibonacci(num - 2);
            }
            answer = ans1 + ans2;
            return answer;
        }
    }

    private int get_answer(){
        return answer;
    }
}

//   OBSERVER
interface Observer{
    public void update();
}

class Consumer implements Runnable, Observer{
    private Producer producer;
    Queue<Integer> output;

    Consumer(Producer producer){
        this.producer = producer;
    }

    public void run(){
        while(true){
//            System.out.println("run");
            run_helper();
        }
    }

    private synchronized void run_helper(){
        try{
//            System.out.println("haha");
            int num = producer.input_queue.take();
            long start = System.nanoTime();
            Fibonacci f = new Fibonacci(num);
            int ans = f.get_fibonacci(num);
//            System.out.println("consumer" + num);
            long end = System.nanoTime();
            long time = end - start;
            producer.output_queue.add(num);
            producer.output_queue.add(ans);
            producer.time_queue.add(time);
        }
        catch(NullPointerException | InterruptedException e){
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Override
    public void update() {
        output = producer.getUpdate();
    }
}

class Producer{
    BlockingQueue<Integer> input_queue = new LinkedBlockingQueue<>();
    private int num_consumer_threads;
    Queue<Integer> output_queue = new LinkedList<>();
    Queue<Long> time_queue = new LinkedList<>();
    private int count = 0;
    private ArrayList<Consumer> consumer;

    Producer(int num_consumer_threads) throws InterruptedException {
        this.num_consumer_threads = num_consumer_threads;
        ArrayList<Consumer> consumer = new ArrayList<Consumer>();
        ArrayList<Thread> consumerThreads = new ArrayList<Thread>();
        for(int i=0; i<num_consumer_threads; i++){
            Consumer new_consumer = new Consumer(this);
            Thread t = new Thread(new_consumer);
            consumerThreads.add(t);
            t.start();
            consumer.add(new_consumer);
        }
    }

    private synchronized void enter_num() throws InterruptedException {
        System.out.println("Enter number whose fibonacci is to be calculated.");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        input_queue.put(n);
//        System.out.println("n " + n);
        notifyAll();
        update_all_consumers();
    }

    private void print_answers(){
        while(output_queue.size() > 0){
            System.out.println("Fibonacci of " + output_queue.poll() + " : " + output_queue.poll() + " Time : " + time_queue.poll() + " nanosecond");
//            System.out.println("output " + output_queue.poll());
        }
    }

    void handle() throws InterruptedException {
//        FACADE
        while(true){
            System.out.println("Enter 1 to give number for fibonacci calculation. Enter 2 to get answers. Enter 3 to exit.");
            Scanner scan = new Scanner(System.in);
            int choice = scan.nextInt();
            switch(choice){
                case 1:
                    enter_num();
                    break;
                case 2:
                    print_answers();
                    break;
                case 3:
                    print_answers();
                    System.exit(1);
                    break;
            }
        }
    }

    Queue<Integer> getUpdate(){
        return output_queue;
    }

    private void update_all_consumers(){
        for(int i=0; i<num_consumer_threads; i++){
            try{
                consumer.get(i).update();
            }
            catch(NullPointerException ignored){

            }
        }
    }
}

public class bonus_lab {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Enter number of consumer threads to be produced");
        Scanner scan = new Scanner(System.in);
        int num_consumer_threads = scan.nextInt();
        Producer producer = new Producer(num_consumer_threads);
        producer.handle();
    }
}
