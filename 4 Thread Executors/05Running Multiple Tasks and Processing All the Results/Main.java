package com.concurrency;

public class Main {

  public static void main(String[] args){
    ExecutorService executor = (ExecutorService)Executors.newCachedThreadPool();

    List<Task> taskList = new ArrayList<>();
    for(int i=0; i<3; i++){
      Task task = new Task("Task-" + i);
      taskList.add(task);
    }

    List<Future<Result>>resultList = null;
    try {
      resultList = executor.invokeAll(taskList);
    } catch (InterruptedException e){
      e.printStackTrace();
    }

    executor.shutdown();

    System.out.printf("Core: Printing the results\n");
    for(int i = 0; i < resultList.size(); i++){
      Future<Result> future = resultList.get(i);
      try {
        Result result = future.get();
        System.out.printf("%s: %s\n", result.getName(), result.getValue());
      } catch (InterruptedException | ExecutionException e){
        e.printStackTrace();
      }
    }
  }
}
