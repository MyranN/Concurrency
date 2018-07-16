package com.concurrency;

public class Event implements Delayed {

  private Date startDate;

  public Event (Date startDate){
    this.startDate = startDate;
  }

  @Override
  public int compareTo(Delayed o){
    long result = this.getDelay(TimeUnit.NANOSECONDS)-o.getDelay(TimeUnit.NANOSECONDS);
    if(result < 0){
      return -1;
    } else if (result > 0){
      return 1;
    }
    return 0;
  }

  /*
  The getDelay() method returns the number of nanoseconds between the activation date
  and the actual date. Both dates are objects of the Date class. You have used the getTime()
  method that returns a date converted to milliseconds and then, you have converted that value
  to TimeUnit received as a parameter. The DelayedQueue class works in nanoseconds, but
  at this point, it's transparent to you.
  */
  @Override
  public long getDelay(TimeUnit unit){
    Date now = new Date();
    long diff = startDate.getTime() - new.getTime();
    return unit.convert(diff, TimeUnit.MILLISECONDS);
  }
}
