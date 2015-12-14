package com.iscte.queque.db;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.iscte.queque.log.LogMessage;
import com.iscte.queque.main.ServerMain.Thread_MessageSave;

//INNER CLASS
public class DBClientData {
	
	/* ATTRIBUTES */
	//LOCKS
	private static Lock lockMessages = new ReentrantLock();//create lock
	private static Lock lockWritersObjectOutputStream = new ReentrantLock();//create lock

	//LISTS
	private static List<String> messages = new ArrayList<String>();
	public static List<ObjectOutputStream> writersObjectOutputStream = new ArrayList<ObjectOutputStream>();
	
	//LOG4J LOGGER
	private static LogMessage logger = new LogMessage();	
	
	//rotina: subtracting an amount from the account
	public static void addMessages(Thread_MessageSave messageSaveThread){
		lockMessages.lock();//acquire lock
		try{
			//LOGGER
			logger.getLog().info("LOCATION: message='"+messageSaveThread.getTexto()+"'; type=shared_resource; class=DBClientData; method=addMessages(messageSaveThread)");
			
			//SAVE OBJECT
			DBClientData.messages.add(messageSaveThread.getTexto());
			
		} catch(Exception ex) {
			logger.getLog().debug(ex);
		} finally {
			lockMessages.unlock();//release lock
		}
	}
	
	//rotina: subtracting an amount from the account
	public static void addNewWriters(ObjectOutputStream writer){
		lockWritersObjectOutputStream.lock();//acquire lock
		try{
			//LOGGER
			logger.getLog().info("LOCATION: type=shared_resource; class=DBClientData; method=addMessages(messageSaveThread)");
			
			//SAVE OBJECT
			DBClientData.writersObjectOutputStream.add(writer);

		} catch(Exception ex) {
			logger.getLog().debug(ex);
		} finally {
			lockWritersObjectOutputStream.unlock();//release lock
		}
	}
	
	//GET
	public static List<ObjectOutputStream> getAllWriters(){
		lockWritersObjectOutputStream.lock();//acquire lock
		List<ObjectOutputStream> newWritersObjectOutputStream = new ArrayList<ObjectOutputStream>();
		try{
			//LOGGER
			logger.getLog().info("GET ALL: writersObjectOutputStream ");
			
			//GET ALL
			for (ObjectOutputStream objectOutputStream : writersObjectOutputStream) {
				newWritersObjectOutputStream.add(objectOutputStream);
			}
			
		} catch(Exception ex) {
			logger.getLog().debug(ex);
		} finally {
			lockWritersObjectOutputStream.unlock();//release lock
		}
		return newWritersObjectOutputStream;
	}			
}
