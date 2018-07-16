package com.concurrency

/*
  There are 2 kinds of exceptions. Checked exceptions that need to be caught or specified via a throws clause in
  the method signature. The other is unchecked exception, which doesn't need to be caught or specified

  When a checked exception is thrown inside run(), they must be caught and treated because run() methods don't allow
  for throws clauses
  When an unchecked exception is thrown inside run(), the default behavior is to write the stack trace and exit
  the program. However, they can be caught by setting the thread with an uncaught exception handler.

  setDefaultUncaughtExceptionHandler() sets the exception handler for all threads. 
*/
public class Main {

  public static void main(String[] args) {

    Task task = new Task();
    Thread thread = new Thread(task);

    thread.setUncaughtExceptionHandler(new ExceptionHandler());
    thread.start();

    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.printf("Thread has finished\n");

  }
}
