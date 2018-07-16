package com.concurrency;

public class Main{

  public static void main(String[] args){

    ScheduledExecutorService executor = (ScheduledExecutorService)Executors.newScheduledThreadPool(1);

    System.out.printf("Main: Starting at: %s\n", new Date());

    for(int i = 0; i < 5; i++){
      Task task = new Task("Task " + i);
      executor.schedule(task, i+1, TimeUnit.SECONDS);
    }

    executor.shutdown();

    try {
      executor.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException){
      e.printStackTrace();
    }

    System.out.printf("Core: Ends at: %s\n", new Date());
  }
}
