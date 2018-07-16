package com.concurrency

public class SafeMain {

  public static void main(String[] args){
    SafeTask task = new SafeTask();

    for(int i = 0; i < 3; i++){
      Thread thread = new Thread(task);
      try{
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e){
        e.printStackTrace();
      }

      thread.start();
    }
  }
}
