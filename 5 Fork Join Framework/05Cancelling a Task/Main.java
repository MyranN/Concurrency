package com.concurrency;

/*
 * There are some points you have to take into account when you want
 * to cancel a task, which are as follows:
    -> The ForkJoinPool class doesn't provide any method to cancel all the tasks it has
       running or waiting in the pool
    -> When you cancel a task, you don't cancel the tasks this task has executed
*/
public class Main {

  public static void main(String[] args){

    ArrayGenerator generator = new ArrayGenerator();
    int array[] = generator.generateArray(1000);

    TaskManager manager = new TaskManager();

    ForkJoinPool pool = new ForkJoinPool();

    SearchNumberTask task = new SearchNumberTask(arraay, 0, 1000, 5, manager);

    pool.execute(task);

    pool.shutdown();

    try {
      pool.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    System.out.printf("Main: The program has finished\n");
  }
}
