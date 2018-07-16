package com.concurrency;

/*
The ConcurrentLinkedDeque class provides more methods to get elements form the list that can be used instead
of pollFirst below:

 -> getFirst() and getLast(): These methods return the first and last element from
    the list respectively. They don't remove the returned element from the list. If the list is
    empty, these methods throw a NoSuchElementExcpetion exception.
 -> peek(), peekFirst(), and peekLast(): These methods return the first and the
    last element of the list respectively. They don't remove the returned element from the
    list. If the list is empty, these methods return a null value.
 -> remove(), removeFirst(), removeLast(): These methods return the first
    and the last element of the list respectively. They remove the returned element
    from the list. If the list is empty, these methods throw a NoSuchElementException
    exception.
*/
public class PollTask implements Runnable{

  private ConcurrentLinkedDeque<String> list;

  public PollTask(ConcurrentLinkedDeque<String> list){
    this.list = list;
  }

  @Override
  public void run(){
    for(int i = 0; i < 5000; i++){
      //Deletes first and last elements
      list.pollFirst();
      list.pollLast();
    }
  }
}
