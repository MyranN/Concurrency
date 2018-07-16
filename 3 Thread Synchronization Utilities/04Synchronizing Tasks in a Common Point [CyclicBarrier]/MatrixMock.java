package com.concurrency

public class MatrixMock{

  private int data[][];

  /**
	 * Generates the bi-dimensional array of numbers.
	 * While generates the array, it counts the times that appears the number we are going
	 * to look for so we can check that the CiclycBarrier class does a good job
   */
  public MatrixMock(int size, int length, int number){

    int counter = 0;
    data = new int[size][length];
    Random random = new Random();
    for(int i=0; i<size; i++){
      for(int j=0; j<length; j++){
        data[i][j] = random.nextInt(10);
        if(data[i][j]==number){
          counter++;
        }
      }
    }
    System.out.printf("Mock: There are %d occurrences of number in generated data.\n", counter, number);
  }

  public int[] getRow(int row){
    if((row)>=0 && (row<data.length)){
      return data[row];
    }
    return null;
  }

}
