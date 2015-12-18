package com.iscte.queque._4serializable._v12_wait_offline.shared_resource;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.iscte.queque._4serializable._v12_wait_offline.interfaces.SharedResource;
import com.iscte.queque._4serializable._v12_wait_offline.message.Message;

//CLASS MULTI-THREAD COM SYNCHRONIZED
public class SharedSync implements SharedResource {

	//ATTRIBUTES ####################################
	//CONSTANTS
	private final int SLEEP = 250;
	//LOCKS
	private Lock lockWritersObjectOutputStream = new ReentrantLock();//create lock

	//LISTS
	private HashMap<String, ArrayList<Message>> mapMessages;
	private List<ObjectOutputStream> writersObjectOutputStream;
	
	//FLAG
	private boolean ocupada = false;
	//###############################################

	//CONTRUCTOR
	public SharedSync() {
		mapMessages = new HashMap<String,ArrayList<Message>>();
		writersObjectOutputStream = new ArrayList<ObjectOutputStream>();
	}

	//sendAllWriters
	@Override
	public synchronized void take(){
		while (!ocupada) {
			System.err.println("The SharedResoure: TAKE [!(ocupada = " + ocupada + ")] - WAITING FOR A RESOURCE TO BE PUT!! Please WAIT...");

			//WAIT
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// ACTION: GET and REMOVE elements to the map
		Message m = getMessage();
		// DELETE OBJECT
		mapMessages.remove(m.getFromUser());
	
		System.err.println("RESOURCE TAKE => " + m.getFromUser() + " - " + m.getMessage());

		
		//SEND
		for (ObjectOutputStream objectOutputStream : writersObjectOutputStream) {
			try {
				objectOutputStream.writeObject(m);
				objectOutputStream.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//SLEEP
		threadSleep(SLEEP);
				
		//RELEASE FLAG AND NOTIFY ALL THREADS
		ocupada = false;
		notifyAll();
	}
	
	/** PRINT: GET ALL DATA FROM mapMessages */
	public Message getMessage(){
	    Message m = null;
		for (Entry<String, ArrayList<Message>> messages : mapMessages.entrySet()) {
			for(Message message : messages.getValue()){
	            m = message;
	        }
	    }
		return m;
	}

	//addMessages
	@Override
	public synchronized void put(Message message) throws InterruptedException {
		//WHILE BUSY: threads in wait mode
		while (ocupada) {
			System.out.println("The SharedResoure: PUT [ocupada = " + ocupada + "] - THERE IS A RESOURCE TO BE TAKEN!! Please WAIT...");
			
			//WAIT
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//OTHERWISE (put and print):
		// ACTION: PUT elements to the map
		// SAVE OBJECT
		ArrayList<Message> messages = new ArrayList<Message>();
		messages.add(message);
		mapMessages.put(message.getFromUser(), messages);
//		mapMessages.put(message.getFromUser(), messages);
		System.out.println("RESOURCE PUT => " + message.getFromUser() + " - " + message.getMessage());
		
		//SLEEP
		threadSleep(SLEEP);
		
		//RELEASE FLAG AND NOTIFY ALL THREADS
		ocupada = true;
		notifyAll();	
		
		
	}
	
	//rotina: subtracting an amount from the account
	public void addNewWriters(ObjectOutputStream writer){
		lockWritersObjectOutputStream.lock();//acquire lock
		try{				
			//SAVE OBJECT
			writersObjectOutputStream.add(writer);
			
			//SLEEP
			threadSleep(250);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			lockWritersObjectOutputStream.unlock();//release lock

		}
	}
	
	/** PRINT: GET ALL DATA FROM mapMessages */
	public void printMapMessages(){
	    for (Entry<String, ArrayList<Message>> messages : mapMessages.entrySet()) {
	        System.out.print(messages.getKey());
	        for(Message message : messages.getValue()){
	            String toReturn = " | ";
	            toReturn += "FROM_USER = " + message.getFromUser() + " | ";
	            toReturn += "MESSAGE = " + message.getMessage() + "\n\t";
//	            toReturn += "TO_USER = " + message.getFromUser();
	        	System.out.print(toReturn+" ");
	        }
	        System.out.println();
	    }
	}
	
	/** PRINT: GET ONE mapMessages FROM A PARAM */
	public void printMapMessages_one(Message message){
	    for (Entry<String, ArrayList<Message>> messages : mapMessages.entrySet()) {
	        if (messages.getKey().equals(message.getFromUser())) {
		    	System.out.print(messages.getKey());		
			}
	        for(Message messageTemp : messages.getValue()){
	            if (message.equals(messageTemp)) {
		            String toReturn = " | ";
		            toReturn += "FROM_USER = " + message.getFromUser() + " | ";
		            toReturn += "MESSAGE = " + message.getMessage() + " | " + "\n\t";
//		            toReturn += "TO_USER = " + message.getFromUser() + " | ";
		        	System.out.print(toReturn+" ");
				}
	        }
	        System.out.println();
	    }
	}
	
	//METHOD SLEEP
	public static void threadSleep(int milis) {
		//SLEEP
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
