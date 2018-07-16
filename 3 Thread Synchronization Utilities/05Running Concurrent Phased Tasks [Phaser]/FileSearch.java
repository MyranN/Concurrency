package com.concurrency;

public class FileSearch implements Runnable {

  private String initPath;

  private String end;

  private List<String> results;

  /**
	 * Phaser to control the execution of the FileSearch objects. Their execution will be divided
	 * in three phases
	 *  1st: Look in the folder and its subfolders for the files with the extension
	 *  2nd: Filter the results. We only want the files modified today
	 *  3rd: Print the results
	 */
	private Phaser phaser;

  public FileSearch(String initPath, String end, Phaser phaser){
    this.initPath = initPath;
    this.end = end;
    this.phaser = phaser;
    results = new ArrayList<>();
  }

  @Override
  public void run(){
    //Waits for the other threads before progressing (the number of threads is the number used to create the Phaser.)
    phaser.arriveAndAwaitAdvance();

    System.out.printf("%s: Starting.\n", Thread.currentThread().getName());

    File file = new File(initPath);
    if(file.isDirectory()){
      directoryProcess(file);
    }

    //Waits for other threads before advancing. If a thread finds no results, it unregisters from the phaser
    //at which point the number of threads to wait on is decremented.
    if(!checkResults()){
      return;
    }

    filterResults();

    if(!checkResults()){
      return;
    }

    showInfo();
    //Finally all remaining threads unregister from the Phaser.
    phaser.arriveAndDeregister();
    System.out.printf("%s: Work completed.\n", Thread.currentThread().getName());
  }

  private void showInfo() {
    for(int i=0; i<results.size(); i++){
      File file = new File(results.get(i));
      System.out.printf("%s: %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
    }

    phaser.arriveAndAwaitAdvance();
  }

  private boolean checkResults() {
    if(results.isEmpty()){
      System.out.printf("%s: Phase %d: 0 results.\n", Thread.currentThread().getName(), phaser.getPhase());
      System.out.printf("%s: Phase %d: End.\n", Thread.currentThread().getName(), phaser.getPhase());

      phaser.arriveAndDeregister();
      return false;
    } else {
      System.out.printf("%s: Phase %d: %d results.\n", Thread.currentThread().getName(),phaser.getPhase(), results.size());
      phaser.arriveAndAwaitAdvance();
      return true;
    }
  }

  //Filter to delete files modified more than a day before now
  private void filterResults(){
    List<String> newResults = new ArrayList<>();
    long actualdate = new Date().getTime();
    for(int i = 0; i < results.size(); i++){
      File file = new File(results.get(i));
      long fileDate = file.lastModified();

      if(actualDate-fileDate < TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)){
        newResults.add(results.get(i));
      }
    }
    results = newResults;
  }

  private void directoryProcess(File file){

    File list[] = file.listFiles();
    if(list != null){
      for(int i = 0; i < list.length; i++){
        if(list[i].isDirectory()){
          directoryProcess(list[i]);
        } else {
          fileProcess(list[i]);
        }
      }
    }
  }

  private void fileProcess(File file) {
    if(file.getName().endsWith(end)){
      results.add(file.getAbsolutePath());
    }
  }
}
