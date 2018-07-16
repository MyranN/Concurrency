package com.concurrency;

/*You can find situations, where you need to send the tasks to the executor in one object and process
the results in another one. For such situations, the Java Concurrency API provides the
CompletionService class.
This behavior has the advantage to share
a CompletionService object, and sends tasks to the executor so the others can process
the results. The limitation is that the second object can only get the Future objects for those
tasks that have finished its execution, so these Future objects can only be used to get the
results of the tasks.
*/

public class Main {

  public static void main(String[] args){
    ExecutorService executor = (ExecutorService)Executors.newCachedThreadPool();
    CompletionService<String> service = new ExecutorCompletionService<>(executor);

    ReportRequest faceRequest = new ReportRequest("Face", service);
    ReportRequest onlineRequest = new ReportRequest("Online", service);
    Thread faceThread = new Thread(faceRequest);
    Thread onlineThread = new Thread(onlineRequest);

    ReportProcessor processor = new ReportProcessor(service);
    Thread senderThread = new Thread(processor);

    System.out.printf("Main: Starting the Threads\n");
    faceThread.start();
    onlineThread.start();
    senderThread.start();

    try {
      System.out.printf("Main: Waiting for the report generators.\n");
      faceThread.join();
      onlineThread.join();
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    System.out.printf("Main: Shutting down the executor.\n");
    executor.shutdown();
    try {
      executor.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    processor.setEnd(true);
    System.out.prinf("Main: End\n");
  }
}
