package com.iscte.queque._2concurrency.shared_resource.v9producer_consumer;

import java.util.Random;

public class Produtor implements Runnable {

	// atributos
	private Ponte ponte;
	private Random random = new Random();// variavel aleatoria

	// construtor
	public Produtor(Ponte ponte) {
		this.ponte = ponte;
	}

	@Override
	public void run() {
		int total = 0;
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(random.nextInt(1000));// 1seg

				// ponte.set(): escrever informacoes da ponte
				total += i;// variavel de controle
				ponte.set(i);
				System.out.println(" => " + total);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
