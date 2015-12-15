package com.iscte.queque._2concurrency.coordenation.synchronized_._vSync_2hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class HashSyncMain {
	
	//ATTRIBUTES
	private HashMap<String,ArrayList<Integer>> map;
	private int count;
	
	//CONSTANTS
	private final int SLEEP = 100;
	private final int ADD_TO_LIST = 20;
	
	//THREADS
	private Thread t1;
	private Thread t2;
	
	//CONSTRUCTOR
	public HashSyncMain() {
		this.map = new HashMap<String,ArrayList<Integer>>();
		this.count = 0;
	}
	
	//GETTER
	public HashMap<String,ArrayList<Integer>> getMap() {
		return map;
	}
	
	public int getCount() {
		return count;
	}

	//MAIN
	public static void main(String[] args) {
		//INSTANTIATE
		HashSyncMain hashSync = new HashSyncMain();
		System.out.println("STARTING THE APP..........................");
		
		//ANNONYMOUS CLASS
		hashSync.t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//LOOP
				hashSync.addElement_map(hashSync.t1, hashSync.ADD_TO_LIST);
			}
		});
		
		//ANNONYMOUS CLASS
		hashSync.t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//LOOP
				hashSync.addElement_map(hashSync.t2, hashSync.ADD_TO_LIST);
			}
		});
		
		//SET NAME
		hashSync.t1.setName("Americo");
		hashSync.t2.setName("Tomas");
		
		//START
		hashSync.t1.start();
		hashSync.t2.start();
		
		//JOIN
		try {
			hashSync.t1.join();
			hashSync.t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//PRINT
		hashSync.displayData();
	}
	
	//PUT
	public synchronized void addElement_map(Thread t, int number) {
		// ACTION: PUT elements to the map
		ArrayList<Integer> integers = new ArrayList<Integer>();
		for (int i = 0; i < number; i++) {
			//ADD
			integers.add(count);
			// SLEEP
			sleepIncount(150);
			//PRINT
			System.err.println(t.getName() + " => ADD COUNT = " + count);
			count++;
		}
		map.put(t.getName(), integers);

	}
	
	//GET
	public synchronized void displayData(){
		for (Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
			//PRINT
			System.out.print(entry.getKey() + " | ");
			// SLEEP
			sleepIncount(150);
			
			for (int integer : entry.getValue()) {
				//PRINT
				System.out.print(integer + " ");
				// SLEEP
				sleepIncount(150);
			}
			System.out.println();
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
	
	//METHOD SLEEP
	public void sleepIncount(int milis) {
		//SLEEP
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}