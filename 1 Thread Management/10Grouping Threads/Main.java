package com.concurrency

public class main {

  public static void main(String[] args){
    ThreadGroup threadGroup = new ThreadGroup("Searcher");
    Result result = new Result();

    SearchTask searchTask = new SearchTask(result);
    for(int i = 0; i < 10; i++) {
      Thread thread = new Thread(threadGroup, searchTask);
      thread.start();
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e){
        e.printStackTrace();
      }
    }

    System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
    System.out.printf("Information about the Thread Group\n");
    threadGroup.list();

    Thread[] threads = new Thread[threadGroup.activeCount()];
    threadGroup.enumerate(threads); //adds the threads in the threadgroup to the array
    for(int i = 0; i < threadGroup.activeCount(); i++){
      System.out.printf("Thread %s: %s\n", threads[i].getName(), threads[i].getState());
    }

    waitFinish(threadGroup);

    threadGroup.interrupt();
  }

  private static void waitFinish(ThreadGrroup threadGroup){
    while(threadGroup.activeCount() > 9){
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e){
        e.printStackTrace();
      }
    }
  }
}
