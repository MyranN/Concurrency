package com.concurrency

public class MyThreadFactory implements ThreadFactory {
  private int counter;
  private String name;
  private List<String> stats;

  public MyThreadFactory(String name){
    counter = 0;
    this.name = name;
    stats = new ArrayList<String>();
  }

  @Override
  public Thread newThread(Runnable r){
    Thread t = new Thread(r, name + "-Thread_" + counter);
    counter++;

    stats.add(String.format("Created thread %d with name %s on %s\n", t.getId(), t.getName(), new Date()));
    return t;
  }

  public String getStats(){
    StringBuffer buffer = new StringBuffer();
    Iterator<String> it = stats.iterator();

    while(it.hasNext()){
      buffer.append(it.next());
    }

    return buffer.toString();
  }
}
