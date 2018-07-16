package com.concurrency;

/*
The getDelay() method is called by the scheduled executor to know if it has to execute
a task. The behavior of this method changes in delayed and periodic tasks. As we
mentioned earlier, the constructor of the MyScheduledClass class receives the original
ScheduledRunnableFuture object that was going to execute the Runnable object and
stores it as an attribute of the class to have access to its methods and its data. When we are
going to execute a delayed task, the getDelay() method returns the delay of the original
task, but in the case of the periodic task, the getDelay() method returns the difference
between the startDate attribute and the actual date.
*/
public class MyScheduledTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {

  //Attribute to store the task that will be used to create a MyScheduledTask
  private RunnableScheduledFuture<V> task;

  private ScheduledThreadPoolExecutor executor;

  private long period;

  private long startDate;

  public MyScheduledTask(Runnable runnable, V result, RunnableScheduledFuture<V> task, ScheduledThreadPoolExecutor executor){
    super(runnable, result);
    this.task = task;
    this.executor = executor;
  }

  /**
	 * Method that returns the reminder for the next execution of the task. If is
	 * a delayed task, returns the delay of the original task. Else, return the difference
	 * between the startDate attribute and the actual date.
	 * @param unit TimeUnit to return the result
	 */
  @Override
  public long getDelay(TimeUnit unit){
    if(!isPeriodic()){
      return task.getDelay(unit);
    } else {
        if(startDate == 0){
          return task.getDelay(unit);
        } else {
          Date now = new Date();
          long delay = startDate - now.getTime();
          return unit.convert(delay, TimeUnit.MILLISECONDS);
        }
    }
  }

  @Override
  public int compareTo(Delayed o){
    return task.compareTo(o);
  }

  @Override
  public boolean isPeriodic(){
    return task.isPeriodic();
  }

  /**
	 * Method that executes the task. If it's a periodic task, it updates the
	 * start date of the task and store in the queue of the executor the task to
	 * be executed again
	 */
  @Override
  public void run(){
    if(isPeriodic() && (!executor.isShutdown())){
      Date now = new Date();
      startDate = now.getTime() + period;
      executor.getQueue().add(this);
    }

    System.out.printf("Pre-MyScheduledTask: %s\n", new Date());
    System.out.printf("MyScheduledTask: Is Periodic: %s\n", isPeriodic());
    super.runAndReset();
    System.out.printf("Post-MyScheduledTask: %s\n", new Date());
  }

  public void setPeriod(long period){
    this.period = period;
  }
}
