package com.concurrency;

public class MyPhaser extends Phaser{

  /*
  Custom methods called upon phase changes.
   */
  @Override
  protected boolean onAdvance(int phase, int registeredParties){
    switch(phase){
      case 0:
        return studentArrived();
      case 1:
        return finishFirstExercise();
      case 2:
        return finishSecondExercise();
      case 3:
        return finishExam();
      default:
        return true;
    }
  }

  private boolean studentArrived(){
    System.out.printf("Phaser: The exam is going to start. The students are ready.\n");
    System.out.printf("Phaser: We have %d students.\n", getRegisteredParties());
    return false;
  }

  private boolean finishFirstExercise(){
    System.out.printf("Phaser: All the students have finished the first exerice.\n");
    System.out.printf("Phaser: It's time for the second one.\n");
    return false;
  }

  private boolean finishSecondExercise(){
    System.out.printf("Phaser: All the students have finished the second exercise.\n");
    System.out.printf("Phaser: It's time for the third one.\n");
    return false;
  }

  private boolean finishExam(){
    System.out.printf("Phaser: All the students have finished the exam.\n");
    System.out.printf("Phaser: Thank you for your time.\n");
    return true;
  }
}
