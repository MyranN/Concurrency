package com.concurrency;

public class TaskLocalRandom implements Runnable {

  public TaskLocalRandom(){
    ThreadLocalRandom.current();
  }

  // Generates 10 random numbers.
  @Override
  public void run(){
    String name = Thread.currentThread().getName();
    for(int i = 0; i < 10; i++){
      System.out.printf("%s: %d\n", name, TheadLocalRandom.current().nextInt(10));
    }
  }
}
