package com.concurrency;

/*
An interesting data structure provided by the Java API, and that you can use in concurrent
applications, is implemented in the DelayedQueue class. In this class, you can store
elements with an activation date. The methods that return or extract elements of the queue
will ignore those elements whose data is in the future. They are invisible to those methods.
To obtain this behavior, the elements you want to store in the DelayedQueue class have to
implement the Delayed interface. This interface allows you to work with delayed objects, so
you will implement the activation date of the objects stored in the DelayedQueue class as
the time remaining until the activation date. This interface forces to implement the following
two methods:
  ->compareTo(Delayed o): The Delayed interface extends the Comparable interface.
    This method will return a value less than zero if the object that is executing the method
    has a delay smaller than the object passed as a parameter, a value greater than zero if
    the object that is executing the method has a delay bigger than the object passed as a
    parameter, and the zero value if both objects have the same delay.
  -> getDelay(TimeUnit unit): This method has to return the time remaining until
    the activation date in the units is specified by the unit parameter. The TimeUnit
    class is an enumeration with the following constants: DAYS, HOURS, MICROSECONDS,
    MILLISECONDS, MINUTES, NANOSECONDS, and SECONDS.
*/
public class Main{

  public static void main(String[] args){

    DelayQueue<Event> queue = new DelayQueue<>();

    Thread threads[] = new Thread[5];

    for(int i = 0; i < threads.length; i++){
      Task task = new Task(i+1, queue);
      threads[i] = new Thread(task);
    }

    for(int i = 0; i < threads.length; i++){
      threads[i].start();
    }

    for (int i = 0; i < threads.length; i++){
      threads[i].join();
    }

    do {
      int counter = 0;
      Event event;
      do {
        event = queue.poll();
        if(event != null) counter++;
      } while (event != null);

      System.out.printf("At %s you have read %d events\n", new Date(), counter);
      TimeUnit.MILLISECONDS.sleep(500);
    } while (queue.size() > 0);
  }
}
