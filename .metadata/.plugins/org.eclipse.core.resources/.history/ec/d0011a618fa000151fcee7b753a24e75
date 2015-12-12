package control_3CD;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Start_SYNCHRONIZED_LOCK_threadCOORDENATION {
	
	//atributos
	private static Account account = new Account();
	
	//main
	public static void main(String[] args) {
		
		//create 2 thread pool 
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new DepositTask());
		executor.execute(new WithdrawTask());
		executor.shutdown();
		
		System.out.println("Thread 1\t\tTread 2\t\tBalance");
	}
	
	//innerClass: task for adding an amount to the account
	public static class DepositTask implements Runnable {

		@Override
		public void run() {	
			try{
				while(true) {
					account.deposit( (int)(Math.random() * 10) + 1);
					Thread.sleep(1000);
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//innerClass: task for subtracting an amount from the account
	public static class WithdrawTask implements Runnable {

		@Override
		public void run() {		
			while(true) {
				account.withdraw( (int)(Math.random() * 10) + 1);
			}
		}
	}
	
	//innerClass: Account
	public static class Account {
		
		//atributos
		private static Lock lock = new ReentrantLock();//create lock
		private static Condition newDeposit = lock.newCondition();//create a condition
		private int balance = 0;
		
		//getter
		public int getBalance() {
			return balance;
		}
		
		//rotina: subtracting an amount from the account
		public void withdraw(int amount){
			lock.lock();//acquire lock
			try{
				while(balance < amount){
					System.out.println("\t\tWait for a deposit...");
					newDeposit.await();//wait on the condition
				}
				
				balance -= amount;
				System.out.println("\t\tWithdraw " + amount + "\t\t" + getBalance());
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			} finally {
				lock.unlock();//release lock
			}
		}
		
		//rotina: adding an amount to the account
		public void deposit(int amount) {
			lock.lock();
			try{
				balance +=amount;
				System.out.println("Deposit: " + amount + "\t\t\t\t\t" + getBalance());
				
				//signal thread wainting on the condition
				newDeposit.signalAll();
			} finally {
				lock.unlock();
			}
		}
	}
}
