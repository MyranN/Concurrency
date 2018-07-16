package com.concurrency;

/*The done()method is called by the FutureTask class when the task that is being controlled
finishes its execution. In this example, you have implemented a Callable object, the
ExecutableTask class, and then, a subclass of the FutureTask class that controls the
execution of the ExecutableTask objects.
The done() method is called internally by the FutureTask class after establishing the
return value and changing the status of the task to the isDone status. You can't change the
result value of the task or change its status, but you can close resources used by the task,
write log messages, or send notifications*/
public class Main {

  public static void main(String[] args){

    ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();

    ResultTask resultTasks[] = new ResultTask[5];
    for(int i = 0; i < 5; i++){
      ExecutableTask executableTask = new ExecutableTask("Task "+ i);
      resultTasks[i] = new ResultTask(executableTask);
      executor.submit(resultTasks[i]);
    }

    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e1){
      e1.printStackTrace();
    }

    /* If tasks have finished before this point, cancel has no effect */
    for (int i = 0; i < resultsTasks.length; i++){
      resultTasks[i].cancel(true);
    }

    /* Print tasks that have not been cancelled */
    for (int i = 0; i < resultTasks.length; i++){
      try {
        if(!resultTasks[i].isCancelled()){
          System.out.printf("%s\n",resultTasks[i].get());
        }
      } catch (InterruptedException | ExecutionException e){
        e.printStackTrace();
      }
    }

    executor.shutdown();
  }
}
