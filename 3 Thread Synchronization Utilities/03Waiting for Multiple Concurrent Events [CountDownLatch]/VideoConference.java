package com.concurrency;

public class VideoConference implements Runnable{

  private final CountDownLatch controller;

  public VideoConference(int number){
    controller = new CountDownLatch(number);
  }

  public void arrive(String name){
    System.out.printf("%s has arrived.\n", name);

    controller.countDown();
    System.out.printf("VideoConference: Waiting for %d participants.\n", controller.getCount());
  }

  @Override
  public void run(){
    System.out.printf("Videoconference: Intialization: %d participants.\n", controller.getCount());
    try{
      controller.await();
      System.out.printf("Videoconference: All the participants have joined\n");
      System.out.printf("Videoconference: Let's start...\n");
    } catch (InterruptedException e){
      e.printStackTrace();
    }
  }
}
