package com.iscte.queque._4serializable._v10_wait_notifyAll.resource.test;

import java.util.Random;

import com.iscte.queque._4serializable._v10_wait_notifyAll.resource.interface_.SharedResource;

public class MessageGet implements Runnable {

	// atributos
	private SharedResource ponte;
	private Random random = new Random();// variavel aleatoria

	// construtor
	public MessageGet(SharedResource ponte) {
		this.ponte = ponte;
	}

	@Override
	public void run() {
		int total = 0;
		for (int i = 0; i < 5; i++) {
			try {
				//SLEEP
				Thread.sleep(random.nextInt(3000));// 1seg

				// ponte.get(): ler informacoes da ponte
				total += ponte.get();// variavel de controle; ler da ponte
				System.err.println(" => " + total);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
