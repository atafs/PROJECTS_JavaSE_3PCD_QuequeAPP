package com.iscte.queque._2concurrency.coordenation.synchronized_._vSync_1variable;

public class VariableSyncMain {
	
	//ATTRIBUTES
	private int count;
	
	//CONSTANTS
	private final int MAX = 20;
	private final int SLEEP = 125;
	
	//THREADS
	private Thread t1;
	private Thread t2;
	
	//CONSTRUCTOR
	public VariableSyncMain() {
		this.count = 0;
	}
	
	//GETTER
	public int getCount() {
		return count;
	}

	//MAIN
	public static void main(String[] args) {
		//INSTANTIATE
		VariableSyncMain variableSync = new VariableSyncMain();
		System.out.println("STARTING THE APP..........................");
		
		//ANNONYMOUS CLASS
		variableSync.t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//LOOP
				for (int i = 0; i < variableSync.MAX; i++) {
					variableSync.incount(variableSync.t1);
				}
				
			}
		});
		
		//ANNONYMOUS CLASS
		variableSync.t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//LOOP
				for (int i = 0; i < variableSync.MAX; i++) {
					variableSync.incount(variableSync.t2);
				}
				
			}
		});
		
		//START
		variableSync.t1.start();
		variableSync.t2.start();
		
		//JOIN
		try {
			variableSync.t1.join();
			variableSync.t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("FINISHING THE APP AFTER JOIN THREADS: count = " + variableSync.count );

	}
	
	//SYNCHRONIZED
	public synchronized void incount(Thread t) {
		//ACTION
		count++;
		
		//PRINT
		if (t.getName().equals("Thread-0")) {
			System.err.println(t.getName() + " => COUNT = " + count);

		} else if (t.getName().equals("Thread-1")) {
			System.out.println(t.getName() + " => COUNT = " + count);
			
		//SLEEP
		sleepIncount();
		}
	}
	
	//METHOD SLEEP
	public void sleepIncount() {
		//SLEEP
		try {
			Thread.sleep(this.SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
