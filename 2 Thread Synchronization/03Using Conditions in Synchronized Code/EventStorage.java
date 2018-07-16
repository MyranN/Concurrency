package com.concurrency

public class EventStorage {

  private int maxSize;

  private List<Date> storage;

  public EventStorage(){
    maxSize=10;
    storage = new LinkedList<>();
  }

  public synchronized void set(){
    while (storage.size() == maxSize){
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    storage.add(new Date());
    System.out.printf("Set: %d", storage.size());
    notify();
  }

  public synchronized void get(){
    while (storage.size() == 0) {
      try {
        wait();
      } catch (InterruptedExceptiion e){
        e.printStackTrace();
      }
    }

    System.out.printf("Get: %d: %s", storage.size(), ((LinkedList<?>)storage).poll());
  }
}
