package com.iscte.queque._2concurrency.shared_resource.v7wait_notifyAll;

import java.util.Random;

public class Consumidor implements Runnable {

	// atributos
	private Ponte ponte;
	private Random random = new Random();// variavel aleatoria

	// construtor
	public Consumidor(Ponte ponte) {
		this.ponte = ponte;
	}

	@Override
	public void run() {
		int total = 0;
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(random.nextInt(3000));// 1seg

				// ponte.get(): ler informacoes da ponte
				total += ponte.get();// variavel de controle; ler da ponte

				// linha com saida de erro (linha vermelha)
				System.err.println(" => " + total);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
