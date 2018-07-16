package com.concurrenct

public class Main {

  public static void main(String[] args) {
    Deque<Event> deque = new ArrayDeque<Event>();

    WriterTask writer = new WriterTask(deque);
    for(int i = 0; i < 3; i++){
      Thread thread = new Thread(writer); //3 threads with the same deque
      thread.start();
    }

    CleanerTask cleaner = new CleanerTask(deque);
    cleaner.start();
  }
}
