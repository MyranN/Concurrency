package com.concurrency;

/*
 * Cannot throw checked Exceptions in compute method but can throw unchecked exceptions. These will generally be
 * swallowed but there are methods to check if an exception was thrown from a task.
 * You can obtain the same result obtained in the example, if instead of throwing an exception,
 * you use the completeExceptionally() method of the ForkJoinTask class. The code
 * would be like the following:
 * Exception e=new Exception("This task throws an Exception: "+ "Task
 * from "+start+" to "+end);
 * completeExceptionally(e);  
 */
public class Main{

  public static void main(String[] args){

    int array[] = new int[100];

    Task task = new Task(array, 0, 100);

    ForkJoinPool pool = new ForkJoinPool();

    pool.execute(task);

    pool.shutdown();

    try {
      pool.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    //Check if an exception was thrown
    if (task.isCompletedAbnormally()){
      System.out.printf("Main: An exception has occurred\n");
      System.out.printf("Main: %s\n", task.getException());
    }

    System.out.printf("Main: Result: %d", task.join());
  }
}
