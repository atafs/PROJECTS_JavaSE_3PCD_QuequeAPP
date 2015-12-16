package com.iscte.queque._2concurrency.shared_resource.v9producer_consumer;

public class Start_CONSUMER_PRODUCER_synchronized_control64 {
	
	public static void main(String[] args) {
		
		//criar uma ponte: partilhada pelo produtor e consumidor
		//criar Threads
//		Ponte ponte = new PonteSynchromized();
//		new Thread(new Produtor(ponte)).start();//produz
//		new Thread(new Consumidor(ponte)).start();//consome
		
		
		//criar Threads
		Ponte ponte = new PonteNaoSynchronized();
		new Thread(new Produtor(ponte)).start();//produz
		new Thread(new Consumidor(ponte)).start();//consome
		
	}

}
