package com.iscte.queque._4serializable._v12_wait_offline.main;

import com.iscte.queque._4serializable._v12_wait_offline.interfaces.SharedResource;
import com.iscte.queque._4serializable._v12_wait_offline.service.ServerService;
import com.iscte.queque._4serializable._v12_wait_offline.shared_resource.SharedSync;


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
