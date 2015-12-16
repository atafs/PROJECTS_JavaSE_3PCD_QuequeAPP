package com.iscte.queque._4serializable._v10_wait_notifyAll.test;

import java.util.Random;

public class MessagePut implements Runnable {

	// atributos
	private SharedResource ponte;
	private Random random = new Random();// variavel aleatoria

	// construtor
	public MessagePut(SharedResource ponte) {
		this.ponte = ponte;
	}

	@Override
	public void run() {
		int total = 0;
		for (int i = 0; i < 5; i++) {
			try {
				//SLEEP
				Thread.sleep(random.nextInt(1000));// 1seg

				// ponte.set(): escrever informacoes da ponte
				total += i;// variavel de controle
				ponte.put(i);
				System.out.println(" => " + total);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
