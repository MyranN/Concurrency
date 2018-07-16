package com.concurrency;

/*
 * A limitation of the Fork/Join framework is that it doesn't allow the cancelation of all the
 * tasks that are in ForkJoinPool. To overcome that limitation, you have implemented the
 * TaskManager class. It stores all the tasks that have been sent to the pool. It has a method
 * that cancels all the tasks it has stored.
*/
public class TaskManager {

  private List<ForkJoinTask<Integer>> tasks;

  public TaskManager(){
    tasks = new ArrayList<>();
  }

  public void addTask(ForkJoinTask<Integer> task){
    tasks.add(task);
  }

  //Cancel all other tasks except the task given as an argument.  
  public void cancelTasks(ForkJoinTask<Integer> cancelTask){
    for(ForkJoinTask<Integer> task: tasks){
      if(task!=cancelTask){
        task.cancel(true);
        ((SearchNumberTask)task).writeCancelMessage();
      }
    }
  }
}
