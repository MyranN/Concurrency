package com.concurrency

public class NetworkConnectionsLoader implements Runnable {

  @Override
  public void run(){
    System.out.printf("Beginning network connections loading: %s\n", new Date());

    try{
      TimeUnit.SECONDS.sleep(6);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.printf("Network connections loading has finished: %s\n", new Date());
  }
}
