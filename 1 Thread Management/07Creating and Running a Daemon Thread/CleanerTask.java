package com.concurrency

//Daemon thread - low priority and normally only execute when no other threads
// in the same program are running. If the only running threads are Daemon
// threads, the JVM ends the program finishing these threads

/*With these characteristics, the daemon threads are normally used as service providers for
normal (also called user) threads running in the same program. They usually have an infinite
loop that waits for the service request or performs the tasks of the thread. They can't do
important jobs because we don't know when they are going to have CPU time and they can
finish any time if there aren't any other threads running. A typical example of these kind of
threads is the Java garbage collector.*/
public class CleanerTask extends Thread {

  private Deque<Event> deque;

  public CleanerTask(Deque<Event> deque) {
    this.deque = deque;
    setDaemon(true); //Sets the thread as a daemon thread
  }

  public void run(){
    while(true){
      Date date = new Date();
      clean(date);
    }
  }

  private void clean(Date date){
    long difference;
    boolean delete;

    if (deque.size() == 0){
      return;
    }

    delete = false;
    do{
      Event e = deque.getLast();
      difference = date.getTime() - e.getDate().getTime();
      if(difference > 10000) {
        System.out.printf("Cleaner: %s\n", e.getEvent());
        deque.removeLast();
        delete=true;
      }
    } while(difference > 10000);

    if(delete){
      System.out.printf("Cleaner: Size of the queue: %d\n", deque.size());
    }
  }
}
