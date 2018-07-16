package com.concurrency;

public class Main {

  public static void main(String[] args) throws Exception {

    MyScheduledThreadPoolExecutor executor = new MyScheduledThreadPool(2);

    Task task = new Task();

    System.out.printf("Main: $s\n", new Date());

    executor.schedule(task,1, TimeUnit.SECONDS);

    TimeUnit.SECONDS.sleep(3);

    task = new Task();

    System.out.printf("Main: %s\n", new Date());

    //Send to the executor a delayed task. It will begin its execution after 1 second, then
    //execute every 3 seconds
    executor.scheduleAtFixedRate(task, 1, 3, TimeUnit.SECONDS);

    TimeUnit.SECONDS.sleep(10);

    executor.shutdown();

    executor.awaitTermination(1, TimeUnit.DAYS);

    System.out.printf("Main: End of the program.\n");
  }
}
