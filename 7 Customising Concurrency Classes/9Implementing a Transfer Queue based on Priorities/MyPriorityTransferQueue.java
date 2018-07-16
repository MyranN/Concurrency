package com.concurrency;

public class MyPriorityTransferQueue<E> extends PriorityBlockingQueue<E> implements TransferQueue<E> {

  private static final long serialVersionUID = 1L;

  /*
  An AtomicInteger attribute, named counter: This attribute stores the number
  of consumers that are waiting for taking an element for the data structure.
  When a consumer calls the take() operation to take an element from the
  data structure, the counter is incremented. When the consumer finishes the
  execution of the take() operation, the counter is decremented again. This
  counter is used in the implementation of the hasWaitingConsumer() and
  getWaitingConsumerCount() methods.
  */
  private AtomicInteger counter;

  private LinkedBlockingQueue<E> transferred;

  private ReentrantLock lock;

  public MyPriorityTransferQueue(){
    counter = new AtomicInteger(0);
    lock = new ReentrantLock();
    transferred = new LinkedBlockingQueue<>();
  }

  /**
	 * This method tries to transfer an element to a consumer waiting a maximum period
	 * of time. If there is a consumer waiting, puts the element in the queue. Else,
	 * puts the element in the queue of transfered elements and wait the specified period of time
	 * until that time pass or the thread is interrupted.

   tryTransfer(E e): This method tries to send an element directly to a consumer.
   If there is a consumer waiting, the method stores the element in the priority queue to
   be consumed immediately by the consumer and then returns the true value. If there
   isn't a consumer waiting, the method returns the false value.
	 */
  @Override
  public boolean tryTransfer(E e){
    lock.lock();
    boolean value;

    if(counter.get() == 0){
      value = false;
    } else {
      put(e);
      value = true;
    }
    lock.unlock();
    return value;
  }

  /**
	 * Transfer an element to the consumer. If there is a consumer waiting,
	 * puts the element on the queue and return the true value. Else, puts the
	 * value in the transfered queue and returns the false value. In this case, the
	 * thread than makes the call will be blocked until a consumer takes the transfered
	 * elements

   transfer(E e): This method transfers an element directly to a consumer. It there
   is a consumer waiting, the method stores the element in the priority queue to be
   consumed immediately by the consumer. Otherwise, the element is stored in the list
   for transferred elements and the thread is blocked until the element is consumed.
   While the thread is put to sleep, you have to free the lock because if not, you block
   the queue.
	 */
  @Override
  public void transfer(E e) throws InterruptedException {
    lock.lock();
    if(counter.get() != 0){
      put(e);
      lock.unlock();
    } else {
      transferred.add(e);
      lock.unlock();
      synchronized (e){
        e.wait();
      }
    }
  }

  /*
  tryTransfer(E e, long timeout, TimeUnit unit): This method is similar to
  the transfer() method, but the thread blocks the period of time determined by its
  parameters. While the thread is put to sleep, you have to free the lock because, if not,
  you block the queue.
  */
  @Override
  public boolean tryTransfer(E e, long timeout, TimeUnit unit) throws InterruptedException{
    lock.lock();
    if(counter.get() != 0){
      put(e);
      lock.unlock();
      return true;
    } else {
      transferred.add(e);
      long newTimeout = TimeUnit.MILLISECONDS.convert(timeout, unit);
      lock.unlock();
      e.wait(newTimeout);
      lock.lock();
      if(transferred.contains(e)){
        transferred.remove(e);
        lock.unlock();
        return false;
      } else {
        lock.unlock();
        return true;
      }
    }
  }

  @Override
  public boolean hasWaitingConsumer(){
    return (counter.get() != 0);
  }

  @Override
  public int getWaitingConsumerCount(){
    return counter.get();
  }

  /**
	 * Method that returns the first element of the queue or is blocked if the queue
	 * is empty. If there is transfered elements, takes the first transfered element and
	 * wake up the thread that is waiting for the transfer of that element. Else, takes the
	 * first element of the queue or is blocked until there is one element in the queue.

   take(): This method returns the next element to be consumed. If there are
   elements in the list of transferred elements, the element to be consumed is taken
   from that list. Otherwise, it is taken from the priority queue.
	 */
  @Override
  public E take() throws InterruptedException {
    lock.lock();
    counter.incrementAndGet();
    E value = transferred.poll();
    if(value == null){
      lock.unlock();
      value = super.take();
      lock.lock();
    } else {
      synchronized(value){
        value.notify();
      }
    }

    counter.decrementAndGet();
    lock.unlock();
    return value;
  }
}
