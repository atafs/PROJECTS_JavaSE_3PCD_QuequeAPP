package com.iscte.queque.client.test.list.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class HashMapMain {

    HashMap<String,ArrayList<Integer>> map = new HashMap<String,ArrayList<Integer>>();

	//MAIN
	public static void main(String args[]) {
		   
	      //##########################################################
	      // Create a hash map
	      HashMap<String, Double> hm = new HashMap<String, Double>();
	      // Put elements to the map
	      hm.put("Zara", new Double(3434.34));
	      hm.put("Mahnaz", new Double(123.22));
	      hm.put("Ayan", new Double(1378.00));
	      hm.put("Daisy", new Double(99.22));
	      hm.put("Qadir", new Double(-19.08));
	      
	      // Get a set of the entries
	      Set set = hm.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
	      // Display elements
	      while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	         //queueLists.get(kv.getKey()).add(message);
	         System.out.print(me.getKey() + ": ");
	         System.out.println(me.getValue());
	      }
	      System.out.println();
	      // Deposit 1000 into Zara's account
	      double balance = ((Double)hm.get("Zara")).doubleValue();
	      hm.put("Zara", new Double(balance + 1000));
	      System.out.println("Zara's new balance: " + hm.get("Zara"));
	      
	      //##########################################################      
	      System.out.println();
	      HashMapMain test = new HashMapMain();
	      test.inputData("mango", 5);
	      test.inputData("apple", 2);
	      test.inputData("grapes", 2);
	      test.inputData("peach", 3);
	      test.displayData();
	   }
	
	
	public void displayData(){
	    for (Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
	        System.out.print(entry.getKey()+" | ");
	        for(int fruitNo : entry.getValue()){
	            System.out.print(fruitNo+" ");
	        }
	        System.out.println();
	    }
	}
	public void inputData(String name ,int number) {
	    Random rndData = new Random();
	    ArrayList<Integer> fruit = new ArrayList<Integer>();
	    for(int i=0 ; i<number ; i++){
	        fruit.add(rndData.nextInt(10));
	    }
	    map.put(name, fruit);
	}

}
