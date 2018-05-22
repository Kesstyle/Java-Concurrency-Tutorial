package Chapter_1_ThreadAPI.Part_7_Join;


import java.util.concurrent.TimeUnit;

/**
 * Last join couldn't wait more than 1 second for t1 thread which is slept for 2 seconds before counter++ operation.
 * <p>
 * The counter resulting value wil be equal 2.
 */
public class Ex_4_Join_with_interruption_during_timeout {
    private static int counter;

    public static void main(String[] args) throws InterruptedException {
        var t1 = new Thread(() -> {
            System.out.println("Run by: " + Thread.currentThread().getName());
            var t2 = new Thread(() -> {
                System.out.println("Run by: " + Thread.currentThread().getName());
                var t3 = new Thread(() -> {
                    System.out.println("Run by: " + Thread.currentThread().getName());
                    counter++;
                });
                t3.start();
                try {
                    t3.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(2); // we are waiting for 2 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter++;
            });
            t2.start();
            try {
                t2.join(); // <-------- wil be interrupted here
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            counter++;
        });

        t1.start();

        t1.join(1000); // we are waiting only 1 second
        t1.interrupt(); // interrupt thread t1 during it's awaiting t2 on t2.join
        counter++;
        System.out.println("Result is " + counter);
    }
}
