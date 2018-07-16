package com.concurrency;

public class MyTask implements Runnable{

  @Override
  public void run(){
    TimeUnit.SECONDS.sleep(2);
  } catch (InterruptedException e){
    e.printStackTrace();
  }
}
