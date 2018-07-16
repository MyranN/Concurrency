package com.concurrency;

public class MyScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

  public MyScheduledThreadPoolExecutor(int corePoolSize){
    super(corePoolSize);
  }

  /**
   * To use a MyScheduledTask task in a scheduled executor, you have overridden the
   * decorateTask() method in the MyScheduledThreadPoolExecutor class. This
   * class extends the ScheduledThreadPoolExecutor executor and that method
   * provides a mechanism to convert the default scheduled tasks implemented by the
   * ScheduledThreadPoolExecutor executor to MyScheduledTask tasks. So, when you
   * implement your own version of scheduled tasks, you have to implement your own version
   * of a scheduled executor.
	 * Method that converts a RunnableScheduledFuture task in a MyScheduledTask task
	 */
  @Override
  protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> task){
    MyScheduledTask<V> myTask = new MyScheduledTask<V>(runnable, null, task, this);
    return myTask;
  }

  /**
	 * Method that schedule in the executor a periodic tasks. It calls the method of its parent class using
	 * the super keyword and stores the period of the task.
	 */
  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDely, long period,
    TimeUnit unit){
      ScheduledFuture<?> task = super.scheduleAtFixedRate(command, initialDelay, period, unit);
      MyScheduledTask<?> myTask = (MyScheduledTask<?>)task;
      myTask.setPeriod(TimeUnit.MILLISECONDS.convert(period, unit));
      return task;
    }
}
