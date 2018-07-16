package com.concurrency;

public class MyWorkerThreadFactory implements ForkJoinWorkerThreadFactory {

  @Override
  public ForkJoinWorkerThread newThread(ForkJoinPool pool){
    return new MyWorkerThread(pool);
  }
}
