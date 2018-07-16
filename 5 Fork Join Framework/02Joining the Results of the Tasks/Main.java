package com.concurrency;

/*
Inside the task, you have to use the structure recommended by the Java API documentation:

If (problem size > size){
  tasks=Divide(task);
  execute(tasks);
  groupResults()
  return result;
} else {
  resolve problem;
  return result;
}

For asynchronous tasks, use complete();
*/
public class Main{

  public static void main(String[] args){

    DocumentMock = new DocumentMock();
    String[][] document = mock.generateDocument(100, 1000, "the");

    DocumentTask task = new DocumentTask(document, 0, 100, "the");

    ForkJoinPool = new ForkJoinPool();

    pool.execute(task);

    do {
      System.out.printf("*******************************\n");
      System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
      System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
      System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
      System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
      System.out.printf("************************************\n");

      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e){
        e.printStackTrace();
      }
    } while (!task.isDone());

    pool.shutdown();

    try {
      pool.awaitTermination(1, TimeUnit.DAYS);
    } catch(InterruptedException e){
      e.printStackTrace();
    }

    try {
      /*To obtain the result returned by Task, you have used the get() method. This method is
        declared in the Future interface implemented by the RecursiveTask class.*/
      System.out.printf("Main: The word appears %d in the document", task.get());
    } catch (InterruptedException | ExecutionException e){
      e.printStackTrace();
    }
  }
}
