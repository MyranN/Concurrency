package com.concurrency;

public class ExecutableTask implements Callable<String> {

  private String name;

  public ExecutableTask(String name){
    this.name = name;
  }

  @Override
  public String call() throws Exception {
    try {
      Long duration = (long) (Math.random() * 10);
      System.out.printf("%s: Waiting %d seconds for results.\n", this.name, duration);
      TimeUnit.SECONDS.sleep(duration);
    } catch (InterruptedException e) {
      //Do nothing
    }

    return "Hello, world. I'm " + name;
  }

  public String getName(){
    return name;
  }
}
