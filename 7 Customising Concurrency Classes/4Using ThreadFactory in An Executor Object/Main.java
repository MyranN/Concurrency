package com.concurrency;

/*
Internally, an Executor framework uses a ThreadFactory interface to create the threads
that it uses to generate the new threads.
*/

public class Main {

  public static void main(String[] args){

    MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactory");

    /*
		 * Create a new ThreadPoolExecutor and configure it for use the
		 * MyThreadFactoryObject created before as the factory to create the threads
		 */
    ExecutorService executor = Executors.newCachedThreadPool(threadFactory);

    MyTask task = new MyTask();

    executor.submit(task);

    executor.shutdown();

    executor.awaitTermination(1, TimeUnit.DAYS);

    System.out.printf("Main: End of the program.\n");
  }
}
