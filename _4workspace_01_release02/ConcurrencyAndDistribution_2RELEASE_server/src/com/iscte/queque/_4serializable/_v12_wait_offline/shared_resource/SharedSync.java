package com.iscte.queque._4serializable._v12_wait_offline.shared_resource;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.iscte.queque._4serializable._v12_wait_offline.enums.Offline_Put_Take;
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
	private HashMap<String, ArrayList<Message>> mapMessages_online;
	private HashMap<String, ArrayList<Message>> mapMessages_offline;
	private HashMap<String, ObjectOutputStream> writersObjectOutputStream;

	//FLAG
	private boolean ocupada_online = false;
	private boolean ocupada_offline = true;
	
	//COUNTER
	private static int counterWriters;
	//###############################################

	//CONTRUCTOR
	public SharedSync() {
		mapMessages_online = new HashMap<String,ArrayList<Message>>();
		mapMessages_offline = new HashMap<String,ArrayList<Message>>();
		writersObjectOutputStream = new HashMap<String, ObjectOutputStream>();

	}

	//sendAllWriters
	@Override
	public synchronized void online_take(){
//		while (!ocupada_online) {
//			System.err.println("The SharedResoure: TAKE [!(ocupada = " + ocupada_online + ")] - WAITING FOR A RESOURCE TO BE PUT!! Please WAIT...");
//
//			//WAIT
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		// ACTION: GET and REMOVE elements to the map
		Message m = online_getMessage();
		// DELETE OBJECT
		mapMessages_online.remove(m.getFromUser());
		// PRINT
		System.err.println("RESOURCE TAKE => " + m.getFromUser() + " - " + m.getMessage());

		
		//SEND
		Set<?> set = writersObjectOutputStream.entrySet();
		Iterator<?> i = set.iterator();
		while (i.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, ObjectOutputStream> writer = (Map.Entry<String, ObjectOutputStream>) i.next();
			try {
				// SAVE WRITEKEY IN MESSAGE
				m.setWriterKey(writer.getKey());
				// WRITE
				writer.getValue().writeObject(m);
				writer.getValue().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		//SLEEP
		threadSleep(SLEEP);
				
//		//RELEASE FLAG AND NOTIFY ALL THREADS
//		ocupada_online = false;
//		notifyAll();
	}
	
	/** PRINT: GET ALL DATA FROM mapMessages */
	public Message online_getMessage(){
	    Message m = null;
		for (Entry<String, ArrayList<Message>> messages : mapMessages_online.entrySet()) {
			for(Message message : messages.getValue()){
	            m = message;
	        }
	    }
		return m;
	}

	//addMessages
	@Override
	public synchronized void online_put(Message message) throws InterruptedException {
//		//WHILE BUSY: threads in wait mode
//		while (ocupada_online) {
//			System.out.println("The SharedResoure: PUT [ocupada = " + ocupada_online + "] - THERE IS A RESOURCE TO BE TAKEN!! Please WAIT...");
//			
//			//WAIT
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		//OTHERWISE (put and print):
		// ACTION: PUT elements to the map
		// SAVE OBJECT
		ArrayList<Message> messages = new ArrayList<Message>();
		messages.add(message);
		mapMessages_online.put(message.getFromUser(), messages);
//		mapMessages.put(message.getFromUser(), messages);
		System.out.println("RESOURCE PUT => " + message.getFromUser() + " - " + message.getMessage());
		
		//SLEEP
		threadSleep(SLEEP);
		
//		//RELEASE FLAG AND NOTIFY ALL THREADS
//		ocupada_online = true;
//		notifyAll();	
		
		
	}
	
	//rotina: subtracting an amount from the account
	public void writer_addNewWriters(ObjectOutputStream writer){
		lockWritersObjectOutputStream.lock();//acquire lock
		try{				
			//SAVE OBJECT
			counterWriters++;
//			//LIST ALL WRITERS
//			writersObjectOutputStream_all.add(writer);
			//HASH ONLINE WRITERS
			writersObjectOutputStream.put(writer.getClass().getSimpleName() + counterWriters, writer);
			
			//SLEEP
			threadSleep(250);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			lockWritersObjectOutputStream.unlock();//release lock

		}
	}
	
	//rotina: subtracting an amount from the account
		public void writer_remove(Message message){
			lockWritersObjectOutputStream.lock();//acquire lock
			try{				
				//SAVE OBJECT
				counterWriters++;
				writersObjectOutputStream.remove(message.getWriterKey());
				
				//SLEEP
				threadSleep(250);
				
			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				lockWritersObjectOutputStream.unlock();//release lock

			}
		}
	
	/** PRINT: GET ALL DATA FROM mapMessages */
	public void online_printMapMessages(){
	    for (Entry<String, ArrayList<Message>> messages : mapMessages_online.entrySet()) {
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
	public void online_printMapMessages_one(Message message){
	    for (Entry<String, ArrayList<Message>> messages : mapMessages_online.entrySet()) {
	        if (messages.getKey().equals(message.getFromUser())) {
		    	System.out.print(messages.getKey());		
			}
	        for(Message messageTemp : messages.getValue()){
	            if (message.equals(messageTemp)) {
		            String toReturn = " | ";
		            toReturn += "FROM_USER = " + message.getFromUser() + " | ";
		            toReturn += "MESSAGE = " + message.getMessage() + " | " + "\n\t";
		        	System.out.print(toReturn+" ");
				}
	        }
	        System.out.println();
	    }
	}
	
		/** PRINT: GET ALL DATA FROM mapMessages */
		public Message offline_getMessage(){
		    Message m = null;
			for (Entry<String, ArrayList<Message>> messages : mapMessages_offline.entrySet()) {
				for(Message message : messages.getValue()){
		            m = message;
		        }
		    }
			return m;
		}

		//addMessages
		@Override
		public synchronized void offline_put_take(Offline_Put_Take OFFLINE_PUT_TAKE, Message message) throws InterruptedException {
			//
			Offline_Put_Take check = OFFLINE_PUT_TAKE;
			switch (check) {
			//PUT
			case PUT:
				// SAVE OBJECT
				ArrayList<Message> messages = new ArrayList<Message>();
				messages.add(message);
				mapMessages_offline.put(message.getFromUser(), messages);
				System.out.println("RESOURCE PUT_OFFLINE => " + message.getFromUser() + " - " + message.getMessage());
				
				//SLEEP
				threadSleep(SLEEP);
				break;
				
			//TAKE
			case TAKE:
				if (mapMessages_offline.size() == 0) {
					return;
				}
				
				// ACTION: GET and REMOVE elements to the map
				Message m = offline_getMessage();
				// DELETE OBJECT
				mapMessages_offline.remove(m.getFromUser());
			
				System.err.println("RESOURCE TAKE => " + m.getFromUser() + " - " + m.getMessage());

				
				//SEND
				Set<?> set = writersObjectOutputStream.entrySet();
				Iterator<?> i = set.iterator();
				while (i.hasNext()) {
					@SuppressWarnings("unchecked")
					Map.Entry<String, ObjectOutputStream> writer = (Map.Entry<String, ObjectOutputStream>) i.next();
					try {
						// SAVE WRITEKEY IN MESSAGE
						m.setWriterKey(writer.getKey());
						// WRITE
						writer.getValue().writeObject(m);
						writer.getValue().flush();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				//SLEEP
				threadSleep(SLEEP);
				break;	

			default:
				System.err.println("SOMETHING IS WRONG... method=offline_put_take");
				break;
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
