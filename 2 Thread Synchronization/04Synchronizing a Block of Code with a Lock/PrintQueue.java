package com.concurrency;

public class PrintQueue {

  //A reentrant lock allows the thread holding the lock to enter other blocks of code also requiring the same
  //lock. A reentrant lock has a counter associated with it. So if the thread holding the lock acquires it again,
  //it must release the lock twice in order to fully release it.
  //The above is the same behaviour as the synchronized keyword. if a thread enters a synchronized block protected
  // by a monitor that the thread already owns, the thread will be allowed to proceed, and the lock will not be
  // released when the thread exits the second (or subsequent) synchronized block, but only will be released when
  // it exits the first synchronized block it entered protected by that monitor

  //Only use a reentrant lock over synchronized if you're going to make use of it's features such as timed wait
  //locks or multiple condition variables.
  private final Lock queueLock = new ReentrantLock();

  //Where synchronized acquires and releases locks in a single scope or block, using locks allows for a more
  //flexible approach, i.e. one that allows locking and releasing across multiple scopes.
  //Also it is not possible to poll or try to acquire a lock without be willing to wait forever for it if using
  //synchronized.
  //When aquiring locks A then B, B must be released before A.
  public void printJob(Object document){
    queueLock().lock();

    try {
      Long duration = (long) (Math.random()*10000);
      System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(),(duration/1000));
      Thread.sleep(duration);
    } catch (InterruptedException e){
      e.printStackTrace();
    } finally {
      queueLock.unlock(); //Always unlock/release in a finally statement otherwise an exception could mean a lock is not
                          //released.
    }
  }
}
