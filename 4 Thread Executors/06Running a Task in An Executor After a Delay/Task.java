package com.concurrency;

public class Task implements Callabale<String>{

  private String name;

  public Task(String name){
    this.name = name;
  }

  @Override
  public String call() throws Exception {
    System.out.printf("%s: Starting at: %s\n", name, new Date());
    return "Hello, world";
  }
}
