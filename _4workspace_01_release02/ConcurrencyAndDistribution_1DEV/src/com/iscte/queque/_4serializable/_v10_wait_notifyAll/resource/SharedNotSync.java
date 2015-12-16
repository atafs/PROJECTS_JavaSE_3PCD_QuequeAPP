package com.iscte.queque._4serializable._v10_wait_notifyAll.resource;

import com.iscte.queque._4serializable._v10_wait_notifyAll.resource.interface_.SharedResource;

public class SharedNotSync implements SharedResource {

	// atributo
	private int valor = -1;// representar o valor da ponte

	@Override
	// classe produtora vai usar este metodo
	public void put(int valor) throws InterruptedException {
		System.out.print("Produziu " + valor);// entrada em preto
		this.valor = valor;

	}

	@Override
	public int get() throws InterruptedException {
		System.err.print("Consumiu " + valor);// saida em vermelho
		return valor;
	}

}
