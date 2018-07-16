package com.concurrency

public class Main {

  public static void main(String[] args){

    UnsafeTask task = new UnsafeTask();

    for(int i=0; i < 3; i++){
      Thread thread = new Thread(task); //Unsafe because the same Unsafe task is being shared on 3 threads. The unsafe
                                        //task has a state.
      thread.start();

      try{
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
