package com.concurrency;

/**
 * This class extends the AbstractQueueSynchronizer class to implement
 * the basis of a Lock. Internally, it uses an AtomicInteger variable
 * to store the state of the lock. It also stores the current thread that
 * has the lock. The tryAcquire()  method and tryRelease() method
 * are the starting point for the Lock implementation
 *
 */
public class MyAbstractQueueSynchronizer extends AbstractQueuedSynchronizer{

  private static final long serialVersionUID = 1L;

  //Attribute that stores the state of the lock. 0 if it's free, 1 if it's busy
  private AtomicInteger state;

  public MyAbstractQueuedSynchronizer(){
    state = new AtomicInteger(0)
  }

  @Override
  protected boolean tryAcquire(int arg){
    return state.compareAndSet(0, 1);
  }

  @Override
  protected boolean tryRelease(int arg){
    return state.compareAndSet(1, 0);
  }
}
