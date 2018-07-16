package com.concurrency;

/*
the operation gets the value of the variable, changes the value in
a local variable, and then tries to change the old value for the new one. If the old value is still
the same, it does the change. If not, the method begins the operation again. This operation is
called Compare and Set.

Atomic variables don't use locks or other synchronization mechanisms to protect the access to
their values. All their operations are based on the Compare and Set operation. It's guaranteed
that several threads can work with an atomic variable at a time without generating data
inconsistency errors and its performance is better than using a normal variable protected
by a synchronization mechanism.
*/
public class Main {

  public static void main(String[] args){

    Account account = new Account();
    account.setBalance(1000);

    Company company = new Company(account);
    Thread companyThread = new Thread(company);

    Bank bank = new Bank(account);
    Thread bankThread = new Thread(bank);

    System.out.printf("Account : Initial Balance: %d\n", account.getBalance());

    companyThread.start();
    bankThread.start();

    try {

      companyThread.join();
      bankThread.join();

      System.out.printf("Account: Final Balance: %d\n", account.getBalance());
    } catch (InterruptedException e){
      e.printStackTrace();
    }
  }
}
