package com.concurrency;

/*
It works as the thread local variables. Every thread that wants to generate random
numbers has a different generator, but all of them are managed from the same class, in a
transparent way to the programmer. With this mechanism, you will get a better performance
than using a shared Random object to generate the random numbers of all the threads.
*/
public class Main {

  public static void main(String[] args){

    Thread[] threads = new Thread[3];

    for (int i = 0; i < threads.length; i++){
      TaskLocalRandom task = new TaskLocalRandom();
      threads[i] = new Thread(task);
      threads[i].start();
    }
  }
}
