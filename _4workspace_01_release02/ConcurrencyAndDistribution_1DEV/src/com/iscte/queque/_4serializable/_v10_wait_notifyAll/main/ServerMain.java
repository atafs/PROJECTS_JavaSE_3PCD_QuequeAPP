package com.iscte.queque._4serializable._v10_wait_notifyAll.main;

import com.iscte.queque._4serializable._v10_wait_notifyAll.interfaces.SharedResource;
import com.iscte.queque._4serializable._v10_wait_notifyAll.resource.SharedSync;
import com.iscte.queque._4serializable._v10_wait_notifyAll.service.ServerService;

public class ServerMain {

	//MAIN
		public static void main(String[] args) throws Exception {
			//SHARED RESOURCE
			SharedResource sharedResource = (SharedResource) new SharedSync();
			
			//SERVER
			ServerService server = new ServerService(sharedResource);
			server.connect_socket();
			
		}
}
