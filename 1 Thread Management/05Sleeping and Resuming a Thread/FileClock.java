package com.concurrency

public class FileClock implements Runnable {

  @Override
  public void run(){
    for(int i = 0; i < 10; i++){
      System.out.printf("%s\n", new Date());

      try {
        TimeUnit.SECONDS.sleep(1); //TimeUnit sleep will implicitly call Thread.sleep. It just easier to read
      } catch (InterruptedException e) {
        System.out.printf("The FileClock has been interrupted");
      }
    }
  }
}
