package com.concurrency

public class Buffer {

  private LinkedList<String> buffer;

  private int maxSize;

  private ReentrantLock lock;

  //Conditions to control that the buffer has lines and empty spaces
  private Condition lines;
  private Condition space;

  //Attribute to control where pending lines are in buffer
  private boolean pendingLines;

  /*
  A thread may acquire an exclusive lock to a critical section of code and then notice that it does not hold the
  necessary conditions in order to proceed with the execution. This thread may then release the lock and change
  its state into a waiting state until the necessary conditions are met. This usually means that another thread
  will later signal the currently waiting thread, which in turn will re-acquire the lock and check if the necessary
  conditions for execution are already met
  Remember that the thread is suspended within a critical code section, ie. inside a lock/unlock code section. This
  means that, even if a given suspended thread receives a wake up signal, it will only resume its execution after no
  other thread is currently executing inside the critical code section.
   */
  public Buffer(int maxSize){
    this.maxSize = maxSize;
    buffer = new LinkedList<String>();
    lock = new ReentrantLock();
    lines = lock.newCondition(); //Create condition for lines
    space = lock.newCondition(); //Create condition for space
    pendingLines = true;
  }

  //Insert a line in the buffer
  public void insert(String line){
    lock.lock();
    try{
      while(buffer.size() == maxSize){ //verify the space condition is met, else wait on the lock condition
        space.await();
      }

      buffer.offer(line);
      System.out.printf("%s: Inserted Line: %d\n", Thread.currentThread().getName(), buffer.size());
      lines.signallAll(); //Signal all threads waiting on the Condition
    } catch (InterruptedExeption e){
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  //Returns a line from the buffer
  public String get() {
    String line = null;
    lock.lock();
    try{
      while ((buffer.size() == 0) && (hasPendingLines())){ //verify the lines condition is met, else wait on the lock condition
        lines.await();
      }

      if(hasPendingLines()){
        line = buffer.poll();
        System.out.printf("%s: Line Read: %d\n", Thread.currentThread().getName(), buffer.size());
        space.signalAll();
      }
    } catch (InterruptedException e){
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    return line;
  }

  public void setPendingLines(boolean pendingLines){
    this.pendingLines = pendingLines;
  }

  public boolean hasPendingLines(){
    return pendingLines || buffer.size() > 0;
  }
}
