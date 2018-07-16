package com.concurrency;

public class Main{

  public static void main(String[] args){

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    System.out.printf("Main: Starting at: %s\n", new Date());

    /*An important point to consider is that the period between two executions is the period of time
    between these two executions that begins. If you have a periodic task that takes 5 sceconds
    to execute and you put a period of 3 seconds, you will have two instances of the task
    executing at a time.*/
    Task task = new Task("Task");
    ScheduledFuture<?> result = executor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);

    for (int i = 0; i < 10; i++){
      System.out.printf("Main: Delay: %d\n", result.getDelay(TimeUnit.MILLISECONDS));
      try {
        TimeUnit.MILLISECONDS.sleep(500);
      } catch (InterruptedException e){
        e.printStackTrace();
      }
    }

    executor.shutdown();
    System.out.printf("Main: No more tasks at: %s\n", new Date());
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    System.out.printf("Main: Finished at: %s\n", new Date());
  }
}
