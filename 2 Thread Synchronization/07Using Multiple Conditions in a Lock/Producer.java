pacakge com.concurrency

public class Producer implement Runnable {

  private FileMock mock;

  private Buffer buffer;

  public Producer(FileMock mock, Buffer buffer){
    this.mock = mock;
    this.buffer = buffer;
  }

  @Override
  public void run(){
    buffer.setPendingLines(true);
    while (mock.hasMoreLines()){
      String line = mock.getLine();
      buffer.insert(line);
    }
    buffer.setPendingLines(false);
  }
}
