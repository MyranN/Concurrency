package com.concurrency

public class Event {

  private Date date;

  private String event;

  public Date getDate(){
    return date;
  }

  public setDate(Date date){
    this.date = date;
  }

  public String getEvent(){
    return event;
  }

  public void setEvent(String event){
    this.event = event;
  }
  
}
