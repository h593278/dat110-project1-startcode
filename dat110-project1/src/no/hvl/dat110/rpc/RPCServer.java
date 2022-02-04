package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private Connection connection;
	
	// hashmap to register RPC methods which are required to extend RPCRemoteImpl	
	private HashMap<Byte,RPCRemoteImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte,RPCRemoteImpl>();
		
	}
	
	public void run() {
		
		// the stop RPC method is built into the server
		RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP,this);
		
		System.out.println("RPC SERVER RUN - Services: " + services.size());
			
		connection = msgserver.accept(); 
		
		System.out.println("RPC SERVER ACCEPTED");
		
		boolean stop = false;
		
		while (!stop) {
	    
		   byte rpcid = 0;
		   Message requestmsg,replymsg;
		   
		   // TODO - START
		   // - receive Message containing RPC request
		   requestmsg = connection.receive();
		   
		   // - find the identifier for the RPC method to invoke
		   byte[] data = requestmsg.getData();
		   rpcid = data[0]; 
		   
		   // - lookup the method to be invoked
		   RPCRemoteImpl metode = services.get(rpcid);
		   
		   // - invoke the method
		   data = RPCUtils.decapsulate(data);
		   //replymsg = new Message());
		   
		   // - send back message containing RPC reply
		   data = RPCUtils.encapsulate(rpcid, metode.invoke(data));
		   replymsg = new Message(data);
		   connection.send(replymsg);
		   
		   
		   if (rpcid == RPCCommon.RPIDSTOP) {
			   stop = true;
		   }
		}
	
	}
	
	// used by server side implementation to register themselves in the RPC server
	public void register(byte rpcid, RPCRemoteImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {
		connection.close();
		msgserver.stop();
		
	}
}
