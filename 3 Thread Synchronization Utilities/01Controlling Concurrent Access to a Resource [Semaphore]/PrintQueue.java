package com.concurrency

public class PrintQueue {

  private final Semaphore semaphore;

  public PrintQueue(){
    semaphore = new Semaphore(1);
  }

  public void printJob (Object document){
    try{

      semaphore.acquire();

      Long duration = (long)(Math.random()*10);
      System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(), duration/1000);
      Thread.sleep(duration);
      TimeUnit.SECONDS.sleep(duration);
    } catch (InterruptedException e) {
        e.printStackTrace();
    } finally {
      semaphore.release();
    }
  }
}
