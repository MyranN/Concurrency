package com.concurrency

//When an uncaught exception is thrown, the JVM looks for the uncaught exeption handler in the thread first. If
//that doesn't exist, the ThreadGroup is checked as in the case below. If that doesn't work, the JVM looks for
// a default handler. If still nothing catches the exception, a stack trace is printed and the program stops
public class MyThreadGroup extends ThreadGroup {

  public MyThreadGroup(String name){
    super(name);
  }

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    //Prints the name of the thread
    System.out.printf("The thread %s has thrown an Exception\n", t.getId());

    //Prints the stack trace of the execution
    e.printStackTrace(System.out);

    //Interrupts the rest of the threads of the thread group
    System.out.printf("Terminating the rest of the Threads\n");

    interrupt();
  }
}
