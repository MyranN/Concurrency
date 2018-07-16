package com.concurrency;

public class Main {

  public static void main(String[] args){

    ProducerConsumerTest test = new ProducerConsumerTest();

    System.out.printf("Main: Starting the test\n");
    TestFramework.runOnce(test);
    System.out.printf("Main: The test has finished\n");
  }
}
