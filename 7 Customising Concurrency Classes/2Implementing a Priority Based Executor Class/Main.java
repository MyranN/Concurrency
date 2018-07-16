package com.concurrency;
/*
Internally, an executor uses a blocking queue to store pending tasks. These are stored in the
order of their arrival to the executor. One possible alternative is the use of a priority queue
to store new tasks. In this way, if a new task with high priority arrives to the executor, it will
be executed before other threads that have already been waiting for a thread to execute, but
have lower priority.
*/
public class Main {

  public static void main(String[] args){

    ThreadPoolExecutor executor = new ThreadPoolExecutor(2,2,1, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());

    for (int i = 0; i < 4; i++){
      MyPriorityTask task = new MyPriorityTask("Task " + i, i);
      executor.execute(task);
    }

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    for (int i = 4; i < 8; i++){
      MyPriorityTask task = new MyPriorityTask("Task " + i, i);
      executor.execute(task);
    }

    executor.shutdown();

    try {
      executor.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    System.out.printf("Main: End of the program.\n");
  }
}
