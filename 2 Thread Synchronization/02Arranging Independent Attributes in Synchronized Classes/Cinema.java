package com.concurrency

public class Cinema {

  private long vacanciesCinema1;
  private long vacanciesCinema2;

  private final Object controlCinema1, controlCinema2;

  public Cinema(){
    controlCinema1 = new Object();
    controlCinema2 = new Object();
    vacanciesCinema1 = 20;
    vacanciesCinema2 = 20;
  }

  //Synchronzing on the method means the entire object is locked so two separate threads cannot access the object
  //at the same time even if they are working on different variables.
  //To get around this, lock on specific objects used purely for locking to better control access
  //
  //Never synchronise on string literals as they are interned which means two separate threads with distinct
  //strings albeit the same value, using them as locks can still block on each other. The synchronized keyword
  //obtains a lock for the monitor associated with the VALUE of the field.
  public boolean sellTickets1 (int number) {
    synchronized (controlCinema1) {
      if(number < vacanciesCinema1) {
        vacanciesCinema1 -= number;
        return true;
      } else {
        return false;
      }
    }
  }

  public boolean sellTickets2 (int number) {
    synchronized (controlCinema2) {
      if(number < vacanciesCinema2) {
          vacanciesCinema2 -= number;
          return true;
      } else {
        return false;
      }
    }
  }

  public boolean returnTickets1 (int number) {
    synchronized (controlCinema1) {
      vacanciesCinema1 += number;
      return true;
    }
  }

  public boolean returnTickets2 (int number) {
    synchronized (controlCinema2) {
      vacanciesCinema2 += number;
      return true;
    }
  }

  public long getVacanciesCinema1(){
    return vacanciesCinema1;
  }

  public long getVacanciesCinema2() {
    return vacanciesCinema2;
  }
  
}
