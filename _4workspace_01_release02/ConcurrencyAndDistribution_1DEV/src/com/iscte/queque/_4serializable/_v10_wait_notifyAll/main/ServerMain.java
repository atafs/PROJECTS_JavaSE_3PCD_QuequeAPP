package com.iscte.queque._4serializable._v10_wait_notifyAll.main;

import com.iscte.queque._4serializable._v10_wait_notifyAll.resource.interface_.SharedResource;
import com.iscte.queque._4serializable._v10_wait_notifyAll.service.ServerService;
import com.iscte.queque._4serializable._v10_wait_notifyAll.service.ServerService.DBClientData;

public class ServerMain {

	//MAIN
		public static void main(String[] args) throws Exception {
			//INSTANTIATE
			new DBClientData();
			
			//SHARED RESOURCE
			SharedResource sharedResource = null;
			
			//SERVER
			ServerService server = new ServerService(sharedResource);
			server.connect_socket();
			
		}
}
