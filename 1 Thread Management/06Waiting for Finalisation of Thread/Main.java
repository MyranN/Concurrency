package com.concurrency

public class Main {

  public static void main(String[] args) {
    DataSourcesLoader dsLoader = new DataSourcesLoader();
    Thread thread1 = new Thread(dsLoader, "DataSourceThread");
    thread1.start();

    NetworkConnectionsLoader ncLoader = new NetworkConnectionsLoader();
    Thread thread2 = new Thread(ncLoader, "NetworkConnectionLoader");
    thread2.start();

    try {
      thread1.join(); //wait for thread1 to finish
      thread2.join(); //wait for thread2 to finish
      //...then continue with rest of code....
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    System.out.printf("Main: Configuration has been loaded: %s\n", new Date());
  }
}
