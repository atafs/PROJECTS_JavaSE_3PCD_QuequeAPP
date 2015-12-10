package com.iscte.queque.client.main;

import com.iscte.queque.client.gui.ClientFrame;

public class ClientMain {
	public static void main(String[] args) {

//		//IMPORTANT ###########################################
//		READ FROM LIST
//		WRITE TO LIST
//		
//		DO NOT READ FROM TXT (to much overhead)
		
//		//PATH=D:\\clouds\\user\\user1.txt
//		String path = "";
//		String userName = "";
//		for(int i = 0; i < args.length; i++) {
//            if (i == 0) {
//            	path = args[i]; 
//			}
//            else if(i == 1){
//            	userName = args[i]; 
//            }
//			System.out.println(args[i]);
//        }
			
		//INSTANTIATE
		new ClientFrame(/*path*/).setVisible(true);
	}
}
