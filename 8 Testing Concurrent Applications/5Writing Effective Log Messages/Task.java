package com.concurrency;

public class Task implements Runnable {

  @Override
  public void run(){
    Logger logger = MyLogger.getLogger(this.getClass().getName());

    logger.entering(Thread.currentThread().getName(), "run()");

    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    logger.exiting(Thread.currentThread().getName(), "run()", Thread.currentThread());
  }
}
