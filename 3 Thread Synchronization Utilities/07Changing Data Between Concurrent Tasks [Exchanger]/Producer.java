package com.concurrency;

public class Producer implements Runnable {

  private List<String> buffer;

  private final Exchanger<List<String>> exchanger;

  public Producer (List<String> buffer, Exchanger<List<String>> exchanger){
    this.buffer = buffer;
    this.exchanger = exchanger;
  }

  /**
	 * Main method of the producer. It produces 100 events. 10 cycles of 10 events.
	 * After produce 10 events, it uses the exchanger object to synchronize with
	 * the consumer. The producer sends to the consumer the buffer with ten events and
	 * receives from the consumer an empty buffer
	 */
  @Override
  public void run(){
    int cycle =1;
    for(int i=0; i<10; i++){
      System.out.printf("Producer: Cycle %d\n", cycle);

      for(int j=0; j<10; j++){
        String message = "Event " + ((i*10) + j);
        System.out.printf("Producer: %s\n", message);
        buffer.add(message);
      }

      try {
        //Change the data buffer with the consumer
        buffer = exchanger.exchange(buffer);
      } catch (InterruptedException e){
        e.printStackTrace();
      }

      System.out.printf("Producer: %d\n", buffer.size());

      cycle++;
    }
  }
}
