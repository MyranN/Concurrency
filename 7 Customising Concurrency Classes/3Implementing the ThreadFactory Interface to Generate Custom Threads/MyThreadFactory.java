package com.concurrency;

public class MyThreadFactory implements ThreadFactory{

  private in counter;

  private String prefix;

  public MyThreadFactory(String prefix){
    this.prefix = prefix;
    counter = 1;
  }

  @Override
  public Thread newThread(Runnable r){
    MyThread myThread = new MyThread(r, prefix+"-"+counter);
    counter++;
    return myThread;
  }
}
