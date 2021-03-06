package Chapter_2_Basic_Concurrency.Part_1_Race_condition;

/**
 * Guard access to shared variable with synchronized section
 * Counter is 0
 */
public class Ex_3_Synchronized_with_Thread_names {
    @SafeAccess
    private static int counter;

    public static void main(String[] args) throws InterruptedException {
        var lock = new Object(); // We are using trivial object to enjoy its monitor's power as a lock

        var t1 = new Thread(() -> {

            for (int i = 0; i < 1_000_000; i++)
                synchronized (lock) {
                    System.out.println("Thread::" + Thread.currentThread().getName() + " enters critical section");
                    counter++;
                    System.out.println("Thread::" + Thread.currentThread().getName() + " leaves critical section");
                }
        });

        var t2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++)
                synchronized (lock) {
                    System.out.println("Thread::" + Thread.currentThread().getName() + " enters critical section");
                    counter--;
                    System.out.println("Thread::" + Thread.currentThread().getName() + " leaves critical section");
                }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Counter = " + counter);
    }

}
