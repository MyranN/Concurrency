package com.concurrency;

/*
  This class extends the ThreadPoolExecutor class to implement a customized executor. It overrides
  the shutdown(), shutdown(), beforeExecute() and afterExecute() to show statistics about the tasks
  executed by the Executor.
*/

public class MyExecutor extends ThreadPoolExecutor {

 /**
 * A HashMap to store the start date of the tasks executed by the executor. When
 * a task finish, it calculates the difference between the start date and the end date
 * to show the duration of the task
 */
  private ConcurrentHashMap<String, Date> startTimes;

  public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                    BlockingQueue<Runnable> workQueue){

     super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
     startTimes = new ConcurrentHashMap<>();
  }

  @Override
  public void shutdown(){
    System.out.printf("MyExecutor: Going to shutdown.\n");
    System.out.printf("MyExecutor: Executed tasks: %d\n", getCompletedTaskCount());
    System.out.printf("MyExecutor: Running tasks: %d\n", getActiveCount());
    System.out.printf("MyExecutor: Pending tasks: %d\n", getQueue().size());
    super.shutdown();
  }

  @Override
  public List<Runnable> shutdownNow(){
    System.out.printf("MyExecutor: Going to immediately shutdown.\n");
    System.out.printf("MyExecutor: Executed tasks: %d\n",getCompletedTaskCount());
    System.out.printf("MyExecutor: Running tasks: %d\n",getActiveCount());
    System.out.printf("MyExecutor: Pending tasks: %d\n",getQueue().size());
    return super.shutdownNow();
  }

  @Override
  protected void beforeExecute(Thread t, Runnable r){
    System.out.printf("MyExecutor: A task is beginning: %s : %s\n", t.getName(), r.hashCode());
    startTimes.put(String.valueOf(r.hashCode(), new Date()));
  }

  @Override
  protected void afterExecute(Runnable r, Throwable r){
    Future<?> result = (Future<?>)r;
    try {
      System.out.printf("***************\n");
      System.out.printf("MyExecutor: A task is finishing.\n");
      System.out.printf("MyExecutor: Result: %s\n", result.get());
      Date startDate = startTimes.remove(String.valueOf(r.hashCode()));
      Date finishDate = new Date();
      long diff = finishDate.getTime() - startDate.getTime();
      System.out.printf("MyExecutor: Duration: %d\n", diff);
    } catch (InterruptedException | ExecutionException e){
      e.printStackTrace();
    }
  }
}
