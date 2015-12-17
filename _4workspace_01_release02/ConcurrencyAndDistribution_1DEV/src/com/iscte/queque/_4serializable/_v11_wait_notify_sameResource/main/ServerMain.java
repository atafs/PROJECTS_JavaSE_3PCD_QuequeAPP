package com.iscte.queque._4serializable._v11_wait_notify_sameResource.main;

import com.iscte.queque._4serializable._v11_wait_notify_sameResource.interfaces.SharedResource;
import com.iscte.queque._4serializable._v11_wait_notify_sameResource.service.ServerService;
import com.iscte.queque._4serializable._v11_wait_notify_sameResource.shared_resource.SharedSync;

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
