pacakge com.concurrency;

/**
 * The Exchanger class only synchronizes two threads

 The consumer begins with an empty buffer and calls Exchanger to synchronize with the
 producer. It needs data to consume. The producer begins its execution with an empty buffer.
 It creates 10 strings, stores it in the buffer, and uses the exchanger to synchronize with
 the consumer.
 At this point, both threads (producer and consumer) are in Exchanger and it changes the
 data structures, so when the consumer returns from the exchange() method, it will have a
 buffer with 10 strings. When the producer returns from the exchange() method, it will have
 an empty buffer to fill again. This operation will be repeated 10 times.

 the first thread that calls the exchange() method was put to sleep until the other threads arrived.
*/
public class Main{
  public static void main(String[] args){

    List<String> buffer1 = new ArrayList<>();
    List<String> buffer2 = new ArrayList<>();

    Exchanger<List<String>> exchanger = new Exchanger<>();

    Producer producer = new Producer(buffer1, exchanger);

    Consumer concumer = new Consumer(buffer2, exchanger);

    Thread threadProducer = new Thread(producer);
    Thread threadConsumer = new Thread(consumer);

    threadProducer.start();
    threadConsumser.start();
  }
}
