package com.concurrency

public class Main {

  /**
   Synchronization provides atomicity and visibility. Atomicity meaning a single thread can access a synchronized
   block of code if and only if it has the monitor lock. Visibility refers to being aware of state changes. A
   state change may be missed if the change was made in memory cache where another thread could not see it.

   An advantage of the synchronized keyword over locks is that it does not require explicit unlocking. This is
   handled by the JVM on exiting a block. Also the JVM handles the locking so it has locking information which is
   very beneficial when debugging thread dumps.

   In contrast, the Locks are just classes and the JVM does not know which locks are owned by which specific
   thread.

   Aside: The volatile keyword also handles visibilty.
   Aside: Ensuring that a thread only accesses a block at any one time is ultimately handled by the locking
          infrastructure of the OS. It may use a compare and swap (CAS) CPU instruction based on a semaphore etc via
          Java's 'Unsafe.class'.
   */

  public static void main(String[] args) {
    Account account = new Account();
    account.setBalance(1000);

    Company company = new Company(account);
    Thread companyThread = new Thread(company);

    Bank bank = new Bank(account);
    Thread bankThread = new Thread(bank);

    System.out.printf("Account : Initial Balance: %f\n", account.getBalance());

    companyThread.start();
    bankThread.start();

    try {
      companyThread.join();
      bankThread.join();

      System.out.printf("Account : Final Balance: %f\n", account.getBalance());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
