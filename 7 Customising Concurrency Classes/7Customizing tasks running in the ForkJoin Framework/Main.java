package com.concurrency;

public class Main {

  public static void main(String[] args){
    int[] array = new int[10000];

    ForkJoinPool pool = new ForkJoinPool();

    Task task = new Task("Task", array, 0, array.length);

    pool.invoke(task);

    pool.shutdown();

    System.out.printf("Main: End of the program.\n"); 
  }
}
