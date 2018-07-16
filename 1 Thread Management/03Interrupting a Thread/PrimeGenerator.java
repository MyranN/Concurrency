package com.concurrency

public class PrimeGenerator extends Thread {

  @override
  public void run(){
    long number = 1L; //1 in long format, otherwise compiler will assume it's int

    while(true) {
      if(isPrime(number)) {
        System.out.printf("Number %d is Prime\n", number);
      }

      //isInterrupted() checks boolean attribute 'interrupted' of thread to see if it's interrupted
      // However,  interrupted() sets the attribute to false after reading. It is also static.
      if(isInterrupted()) {
        System.out.printf("The Prime Generator has been interrupted\n");
        return;
      }

      number++;
    }
  }

  private boolean isPrime(long number){
    if (number <= 2) {
      return true;
    }

    for(long i=2; i<number; i++){
      if((number % i) == 0) {
        return false;
      }
    }

    return true;
  }
}
