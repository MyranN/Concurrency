package com.concurrency

public class Main {

  public static void main(String[] args){
    Thread task = new PrimeGenerator();
    task.start();

    //Wait 5 seconds
    try {
      TimeUnits.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //Interrupt thread
    task.interrupt();
  }
}
