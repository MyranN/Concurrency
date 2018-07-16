package com.concurrency;

public class ResultTask extends FutureTask<String> {

  private String name;

  public ResultTask(Callable<String> callable){
    super(callable);
    this.name = ((ExecutableTask)callable).getName();
  }

  @Override
  protected void done(){
    if(isCancelled()){
      System.out.printf("%s: Has been cancelled\n", name);
    } else {
      System.out.printf("%s: Has finished\n", name);
    }
  }
}
