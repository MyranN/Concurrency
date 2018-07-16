package com.concurrency

public class UnsafeTask implements Runnable {

  private Date startDate; //this task has state

  @Override
  public void run(){
    startDate = new Date();
    System.out.printf("Starting Thread: %s : %s\n", Thread.currentThread().getId(), startDate);
    try {
      TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.printf("Thread Finished: %s : %s\n", Thread.currentThread.getId(), startDate);
  }
}
