package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.*;

public class SensorStub extends RPCLocalStub {

	private byte RPCIDREAD = 1;
	
	public SensorStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public int read() {
		
		int temp = 0;
		
		// implement marshalling, call and unmarshalling for read RPC method
		
		byte[] request = RPCUtils.marshallInteger(temp);
		byte[] response = rpcclient.call(RPCIDREAD, request);
		temp = RPCUtils.unmarshallInteger(response);
		
		return temp;
	}
	
}
