package com.iscte.queque._4serializable._v10_wait_notifyAll.resource;

import com.iscte.queque._4serializable._v10_wait_notifyAll.resource.interface_.SharedResource;

//CLASS MULTI-THREAD COM SYNCHRONIZED
public class SharedSync implements SharedResource {

	// atributos
	private int valor = -1;// a ser compartilhado
	private boolean ocupada = false;// especie de flag

	// semelhante ao funcionamento da impressora

	@Override
	// executar um de cada vez o metodo set(i);
	public synchronized void put(int valor) throws InterruptedException {

		while (ocupada) {// enquanto ela estiver ocupada
			System.out.println("A PONTE ESTA CHEIA!! Produtor deve aguardar...");
			wait();// da classe Object, forma de espera
		}
		System.out.println("Produziu " + valor);// entrada em preto
		this.valor = valor;
		ocupada = true;// a partir do momento em que voltou a guardar outro
						// valor, ela voltou a estar ocupada (status da ponte)
		notifyAll();// notifico as Threads

	}

	@Override
	public synchronized int get() throws InterruptedException {

		while (!ocupada) {// enquanto estiver vazia, nao tenho de processar mais// nada
			System.out.println("PONTE ESTA VAZIA!! Consumidora a aguardar...");
			wait();// ate a ponte estar ocupada, e executar a leitura do que// esta la dentro
		}

		System.err.println("Consumiu " + valor);// saida em vermelho
		ocupada = false;// acabou de ser lido
		notifyAll();// notificar todas as Threads que a variavel mudou
		return valor;
	}

}
