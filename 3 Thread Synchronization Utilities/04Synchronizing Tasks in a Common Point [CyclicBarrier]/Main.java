package com.concurrency

/**
The problem resolved in the example is simple. We have a big matrix of random integer
numbers and you want to know the total number of occurrences of a number in this matrix.
To get a better performance, we use the divide and conquer technique. We divide the matrix
in five subsets and use a thread to look for the number in each subset. These threads are
objects of the Searcher class.
*/
public class Main{

  public static void main(String[] args){

    final int ROWS=10000;
    final int NUMBERS=1000;
    final int SEARCH=5;
    final int PARTICIPANTS=5;
    final int LINES_PARTICIPANT=2000;
    MatrixMock mock = new MatrixMock(ROWS, NUMBERS, SEARCH);

    Results results = new Results(ROWS);

    Grouper grouper = new Grouper(results);

    /*
    A cyclic barrier is used to progress threads at the same time. By calling barrier.await(), threads are held
    at the barrier until the number of threads specified in the instantiation of the cyclic barrier reach the
    point where barrier.await() is called.
    A cyclic barrier can be provided a Runnable task that runs when all threads reach the barrier. In this case,
    the Grouper task.
     */
    CyclicBarrier barrier = new CyclicBarrier(PARTICIPANTS, grouper);

    Searcher[] searchers = new Searcher[PARTICIPANTS];

    for(int i=0; i<PARTICIPANTS; i++){
      searchers[i] = new Searcher(i*LINES_PARTICIPANT, (i*LINES_PARTICIPANT) + LINES_PARTICIPANT, mock, results, 5, barrier);
      Thread thread = new Thread(searchers[i]);
      thread.start();
    }
    System.out.printf("Main: The main thread has finished\n");
  }
}
