package com.iscte.queque._3lists.queue;

import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueMain {

	//MAIN
	public static void main(String[] args) {

		// LISTS
		Queue<String> queueB = new PriorityQueue<>();

		// ADD
		queueB.add("element 1");
		queueB.add("element 2");
		queueB.add("element 3");

		// PEEK: element at head of the queue
		Object firstElement = queueB.element();
		System.out.println("\nfirstElement=" + firstElement.toString());

		System.out.println("\n----------PRINT ALL--------------");
		for (Object object : queueB) {
			System.out.println("object=" + object.toString());
		}
		
		// REMOVE
		Object removeFirstElement = queueB.remove();
		System.err.println("\nremoveFirstElement=" + removeFirstElement.toString());
		
		System.err.println("\n----------PRINT ALL--------------");
		for (Object object : queueB) {
			System.err.println("object=" + object.toString());
		}
	}

}
