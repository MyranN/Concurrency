package com.concurrency;

public class MyWorkerThread extends ForkJoinWorkerThread {

 /**
 * ThreadLocal attribute to store the number of tasks executed by each thread
 */
  private static ThreadLocal<Integer> taskCounter = new ThreadLocal<>();

  protected MyWorkerThread(ForkJoinPool pool){
    super(pool);
  }

  @Override
  protected void onStart(){
    super.onStart();
    System.out.printf("MyWorkerThread %d: Initializing task counter.\n", getId());
    taskCounter.set(0);
  }

  @Override
  protected void onTermination(Throwable exception){
    System.out.printf("MyWorkerThread %d: %d\n", getId(), taskCounter.get());
    super.onTermination(exception);
  }

  public void addTask(){
    int counter = taskCounter.get().intValue();
    counter++;
    taskCounter.set(counter);
  }
}
