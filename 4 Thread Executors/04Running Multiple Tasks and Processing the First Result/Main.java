package com.concurrency;

public class Main{

  public static void main(String[] args){
    String username = "test";
    String password = "test";

    UserValidator ldapValidator = new UserValidator("LDAP");
    UserValidator dbValidator = new UserValidator("DataBase");

    TaskValidator ldapTask = new TaskValidator(ldapValidator, username, password);
    TaskValdator dbTask = new TaskValidator(dbValidator, username, password);

    List<TaskValidator> taskList = new ArrayList<>();
    taskList.add(ldapTask);
    taskList.add(dbTask);

    ExecutorService executor = (ExecutorService)Executors.newCachedThreadPool();
    String result;
    try {
      // Send the list of tasks to the executor and waits for the result of the first task
			// that finish without throw an Exception. If all the tasks throw an Exception, the
			// method throws and ExecutionException.
      result = executor.invokeAny(taskList);
      System.out.printf("Main: Result: %s\n", result);
    } catch (InterruptedException e){
      e.printStackTrace();
    } catch (ExecutionException e){
      e.printStackTrace();
    }

    executor.shutdown();
    System.out.printf("Main: End of the Execution\n");
  }
}
