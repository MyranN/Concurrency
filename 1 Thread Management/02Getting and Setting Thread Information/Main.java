package com.concurrency

public class Main{

  public static void main(String[] args) {
    System.out.printf("Minimum Priority: %s\n", Thread.MIN_PRIORITY);
    System.out.printf("Normal Priority: %s\n", Thread.NORM_PRIORITY);
    System.out.printf("Maximum Priority: %s\n", Thread.MAX_PRIORITY);

    Thread threads[];
    Thread.State status[]; //Array holding the state of each Thread

    threads = new Thread[10];
    status = new Thread.State[10];
    for(int i = 0; i<10; i++){
      threads[i] = new Thread(new Calculator(i));
      if((i%2) == 0){
        threads[i].setPriority(Thread.MAX_PRIORITY);
      } else {
        threads[i].setPriority(Thread.MIN_PRIORITY);
      }
      threads[i].setName("Thread " + i);
    }

    //try with resources
    try (FileWriter file = new FileWriter(".\\data\\log.txt"); PrintWriter pw = new PrintWriter(file)) {

      //Get initial state
      for(int i=0; i<10; i++){
        pw.println("Main : Status of Thread "+ i + ": " + threads[i].getState());
        status[i] = threads[i].getState();
      }

      for(int i=0; i<10; i++){
        threads[i].start();
      }

      boolean finish=false;
      while(!finish) {
        for(int i=0; i<10; i++){
          if(threads[i].getState() != status[i]) {
            writeThreadInfo(pw, threads[i], status[i])
            status[i] = threads[i].getState();
          }
        }

        finish=true;
        for (int i = 0; i<10; i++){
          finish = finish && (threads[i].getState() == State.TERMINATED);
        }
      }
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  private static void writeThreadInfo(PrintWriter pw, Thread thread, State state){
    pw.printf("Main : Id %d - %s\n", thread.getId(), thread.getName());
    pw.printf("Main : Priority: %d\n", thread.getPriority());
    pw.printf("Main : Old State: %s\n", state);
  }
}
