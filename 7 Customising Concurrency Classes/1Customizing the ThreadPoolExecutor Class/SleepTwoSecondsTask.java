package com.concurrency;

public class SleepTwoSecondsTask implements Callable<String>{

  /*
  Sleeps 2 seconds and returns date/time
  */
  public String call() throws Exception {
    TimeUnit.SECONDS.sleep(2);
    return new Date().toString();
  }
}
