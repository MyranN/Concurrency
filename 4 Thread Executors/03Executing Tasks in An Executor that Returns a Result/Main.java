package com.concurrency;

public class Main{

  public static void main(String[] args){
    ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);

    List<Future<Integer>> resultList = new ArrayList<>();

    Random random = new Random();
    for(int i = 0; i < 10; i++){
      Integer number = new Integer(random.nextInt(10));
      FactorialCalculator calculator = new FactorialCalculator(number);
      Future<Integer> result = executor.submit(calculator);
      resultList.add(result);
    }

    do {
      System.out.printf("Main: Number of Completed Tasks: %d\n", executor.getCompletedTaskCount());
      for(int i=0; i<resultList.size(); i++){
        Future<Integer> result = resultList.get(i);
        System.out.printf("Main: Task %d: %s\n",i,result.isDone());
      }
      try {
        Thread.sleep(50);
      } catch (InterruptedException e){
        e.printStackTrace();
      }
    } while (executor.getCompletedTaskCount() < resultList.size());

    System.out.printf("Main: Results\n");
    for (int i=0; i < resultList.size(); i++){
      Future<Integer> result = resultList.get(i);
      Integer number = null;
      try {
        number = result.get();
      } catch (InterruptedException e){
        e.printStackTrace();
      } catch (ExecutionException e){
        e.printStackTrace();
      }
      System.out.printf("Core: Task %d: %d\n", i, number);
    }

    executor.shutdown();
  }
}
