pacakge com.concurrency

public class Main{

  public static void main(String[] args) {

    //Creates a simulated file with 100 lines;
    FileMock mock = new FileMock(101,10);

    //Creates a buffer with a maximum of 20lines
    Buffer buffer = new Buffer(20);

    Producer producer = new Producer(mock, buffer);
    Thread threadProducer = new Thread(producer, "Producer");

    Consumer[] consumers = new Consumer[3];
    Thread[] threadConsumers = new Thread[3];

    for(int i=0; i<3; i++){
      consumers[i] = new Consumer(buffer);
      threadConsumers[i] = new Thread(consumers[i], "Consumer " + i);
    }

    threadProducer.start();
    for(int i=0; i<3; i++){
      threadConsumers[i].start();
    }
  }
}
