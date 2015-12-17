package com.iscte.queque._4serializable._v10_wait_notifyAll.shared_resource;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.iscte.queque._4serializable._v10_wait_notifyAll.interfaces.SharedResource;
import com.iscte.queque._4serializable._v10_wait_notifyAll.message.Message;
import com.iscte.queque._4serializable._v10_wait_notifyAll.thread.reader.MessagePut;

//CLASS MULTI-THREAD COM SYNCHRONIZED
public class SharedSync implements SharedResource {

	//ATTRIBUTES ####################################
	//CONSTANTS
	private final int SLEEP = 250;
	//LOCKS
	private static Lock lockWritersObjectOutputStream = new ReentrantLock();//create lock

	//LISTS
	private static ArrayList<Message> messages;
	private static HashMap<String,ArrayList<Message>> mapMessages;
	private static List<ObjectOutputStream> writersObjectOutputStream;
	
	//FLAG
	private boolean ocupada = false;
	//###############################################

	//CONTRUCTOR
	public SharedSync() {
		messages = new ArrayList<Message>();
		mapMessages = new HashMap<String,ArrayList<Message>>();
		writersObjectOutputStream = new ArrayList<ObjectOutputStream>();
	}
	
	//addMessages
	@Override
	public synchronized void put(MessagePut messagePut){
		
		//WHILE BUSY: threads in wait mode
		while (ocupada) {
			System.out.println("The SharedResoure is BUSY!! please wait...");
			
			//WAIT
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//OTHERWISE: 
		System.out.println("RESOURCE PUT => " + messagePut.getMessage().getFromUser() + " - " + messagePut.getMessage().getMessage());
		
		// ACTION: PUT elements to the map
		messages.add(messagePut.getMessage());
		// SAVE OBJECT
		mapMessages.put("RESOURCE PUT => " + messagePut.getMessage().getFromUser(), messages);

		//SLEEP
		threadSleep(SLEEP);
		
		//RELEASE FLAG AND NOTIFY ALL THREADS
		ocupada = true;
		notifyAll();
		
	}
	
//	@Override
//	// executar um de cada vez o metodo set(i);
//	public synchronized void put(int valor) throws InterruptedException {
//
//		while (ocupada) {// enquanto ela estiver ocupada
//			System.out.println("A PONTE ESTA CHEIA!! Produtor deve aguardar...");
//			wait();// da classe Object, forma de espera
//		}
//		System.out.println("Produziu " + valor);// entrada em preto
//		this.valor = valor;
//		ocupada = true;// a partir do momento em que voltou a guardar outro
//						// valor, ela voltou a estar ocupada (status da ponte)
//		notifyAll();// notifico as Threads
//
//	}
	
	//sendAllWriters
	@Override
	public synchronized void get(Message message){
		while (!ocupada) {
			System.out.println("SHARED RESOURCE IS EMPTY!! Please wait...");
			
			//WAIT
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//GET ALL
		for (ObjectOutputStream objectOutputStream : writersObjectOutputStream) {
			try {
				objectOutputStream.writeObject(message);
				objectOutputStream.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.err.println("RESOURCE GET => " + message.getFromUser() + " - " + message.getMessage());
		
		//SLEEP
		threadSleep(SLEEP);
				
		//RELEASE FLAG AND NOTIFY ALL THREADS
		ocupada = false;
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
	
//	@Override
//	public synchronized int get() throws InterruptedException {
//
//		while (!ocupada) {// enquanto estiver vazia, nao tenho de processar mais// nada
//			System.out.println("PONTE ESTA VAZIA!! Consumidora a aguardar...");
//			wait();// ate a ponte estar ocupada, e executar a leitura do que// esta la dentro
//		}
//
//		System.err.println("Consumiu " + valor);// saida em vermelho
//		ocupada = false;// acabou de ser lido
//		notifyAll();// notificar todas as Threads que a variavel mudou
//		return valor;
//	}
//	// atributos
//	private int valor = -1;// a ser compartilhado

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
