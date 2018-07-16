package com.concurrency;

public class Consumer implements Runnable{

  private List<String> buffer;

  private final Exchanger<List<String>> exchanger;

  public Consumer(List<String> buffer, Exchanger<List<String>> exhanger){
    this.buffer = buffer;
    this.exchanger = exchanger;
  }

  /**
	 * Main method of the consumer. It consumes all the events produced by the Producer. After it
	 * processes ten events, it uses the exchanger object to synchronize with
	 * the producer. It sends to the producer an empty buffer and receives a buffer with ten events
	 */
  @Override
  public void run(){
    int cycle = 1;

    for (int i=0; i<10; i++){
      System.out.printf("Consumser: Cycle %d\n", cycle);

      try{
        // Wait for the produced data and send the empty buffer to the producer
        buffer = exchanger.exchange(buffer);
      } catch (InterruptedException e){
        e.printStackTrace();
      }

      System.out.printf("Consumer: %d\n", buffer.size());

      for (int j=0; j<10; j++){
        String message = buffer.get(0);
        System.out.printf("Consumer: %s\n", message);
        buffer.remove(0);
      }

      cycle++;
    }
  }
}
