package com.concurreny;

/*
 * You should be aware of a big difference between the two methods. When you use the
 * synchronized methods, the task that calls one of these methods (for example, the
 * invokeAll() method) is suspended until the tasks it sent to the pool finish their execution.
 * This allows the ForkJoinPool class to use the work-stealing algorithm to assign a new
 * task to the worker thread that executed the sleeping task. On the contrary, when you use
 * the asynchronous methods (for example, the fork() method), the task continues with its
 * execution, so the ForkJoinPool class can't use the work-stealing algorithm to increase the
 * performance of the application. In this case, only when you call the join() or get() methods
 * to wait for the finalization of a task, the ForkJoinPool class can use that algorithm.
*/

public class Main{

  public static void main(String[] args){

    ForkJoinPool pool = new ForkJoinPool();

    FolderProcessor system = new FolderProcessor("C:\\Windows", "log");
    FolderProcessor apps = new FolderProcessor("C:\\Program Files", "log");
    FolderProcessor documents = new FolderProcessor("C:\\Documents And Settings", "log");

    pool.execute(system);
    pool.execute(apps);
    pool.execute(documents);

    do {
      System.out.printf("************************\n");
      System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
      System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
      System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
      System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
      System.out.printf("*************************\n");
      try{
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e){
        e.printStackTrace();
      }
    } while ((!system.isDone())|| (!apps.isDone()) || (!documents.isDone()));

    pool.shutdown();

    List<String> results;

    results = system.join();
    System.out.printf("System: %d files found.\n", results.size());

    results = apps.join();
    System.out.printf("Apps: %d files found.\n", results.size());

    results = document.join();
    System.out.printf("Document: %d files found.\n", results.size());
  }
}
