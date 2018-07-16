package com.concurrency

public class PrintQueue {

/**
 * Creates a lock to control the access to the queue.
 * With the boolean attribute, we control the fairness of
 * the Lock
 */
  private final Lock queueLock = new ReentrantLock(true);

 /**
  *All threads are created with a difference of 0.1 seconds. The first thread that requests the
  *control of the lock is Thread 0, then Thread 1, and so on. While Thread 0 is running the
  *first block of code protected by the lock, we have nine threads waiting to execute that block
  *of code. When Thread 0 releases the lock, immediately, it requests the lock again, so we
  *have 10 threads trying to get the lock. As the fair mode is enabled, the Lock interface will
  *choose Thread 1, so it's the thread that has been waiting for more time for the lock. Then, it
  *chooses Thread 2, then, Thread 3, and so on. Until all the threads have passed the first block
  *protected by the lock, none of them will execute the second block protected by the lock.
  *Once all the threads have executed the first block of code protected by the lock, it's the turn of
  *Thread 0 again. Then, it's the turn of Thread 1, and so on.
  *
  * There is a performance cost to ensuring fairness, by default fairness is set to false.
  *
  * Aside: Synchronized is not fair but does guarantee statistical fairness. That is to say, all threads will
  * eventually acquire the lock. Statistical fairness is more performant and is generally sufficient enough
  * compared to deterministic fairness.
  */
  public void printJob(Object document){
    queueLock.lock();

    try {
      Long duration = (long) (Math.random() * 10000);
      System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(), (duration/1000));
      Thread.sleep(duration);
    } catch (InterruptedException e){
      e.printStackTrace();
    } finally {
      queueLock.unlock();
    }

    queueLock.lock();
    try {
      Long duration=(long)(Math.random()*10000);
      System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",Thread.currentThread().getName(),(duration/1000));
      Thread.sleep(duration);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
      queueLock.unlock();
      }
    }
}
