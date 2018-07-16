package com.concurrency
/*
If you create an object of a class that implements the Runnable interface and then start
various Thread objects using the same Runnable object, all the threads share the same
attributes of the Runnable object. This means that, if you change an attribute in a thread,
all the threads will be affected by this change.

Sometimes, you will be interested in having an attribute that won't be shared between all the
threads that run the same object. The Java Concurrency API provides a clean mechanism
called thread-local variables with a very good performance.
*/
public class SafeTask implements Runnable {

  //ThreadLocal variable shared between threads
  private static Threadlocal<Date> startDate = new ThreadLocal<Date>() {
    @Override
    protected Date initialValue(){
      return Date();
    }
  };

  @Override
  public void run(){
    System.out.printf("Starting Thread: %s : %s\n", Thread.currentThread().getId(), startDate.get());
    try {
      TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.printf("Thread Finished: %s : %s\n", Thread.currentThread().getId().startDate.get());
  }
}
