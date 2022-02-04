package no.hvl.dat110.rpc;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.Connection;

public class RPCUtils {

	public static byte[] encapsulate(byte rpcid, byte[] payload) {

		byte[] rpcmsg = null;

		// TODO - START
		
		// Encapsulate the rpcid and payload in a byte array according to the RPC
		// message syntax
		rpcmsg = new byte[payload.length+1]; // payload.lenth+1 eller 128?
		rpcmsg[0]=rpcid;
		
		for (int i=0; i<payload.length; i++) {
			rpcmsg[i+1]=payload[i];
		}

		// TODO - END

		return rpcmsg;
	}

	public static byte[] decapsulate(byte[] rpcmsg) {

		byte[] payload = null;

		// TODO - START

		// Decapsulate the rpcid and payload in a byte array according to the RPC
		// message syntax
		
		payload = new byte[rpcmsg.length-1];
		for (int i=0; i<payload.length; i++) {
			payload[i]=rpcmsg[i+1];
		}
		
		
		// TODO - END

		return payload;

	}

	public static byte[] marshallString(String str) {

		byte[] encoded = new byte[str.length()];

		for (int i = 0; i < str.length(); i++) {
			encoded[i] = (byte) str.charAt(i);
		}

		return encoded;
	}

	public static String unmarshallString(byte[] data) {

		String decoded = null;
		
		decoded = new String(data);

		return decoded;
	}

	public static byte[] marshallVoid() {

		//SKal me gjer noko meir her?????
		byte[] encoded = null;

		//Snakka om noko sånt på discord
		encoded = new byte[0];

		return encoded;

	}

	public static void unmarshallVoid(byte[] data) {

		//Me gjer ingen ting????

	}

	public static byte[] marshallBoolean(boolean b) {

		byte[] encoded = new byte[1];

		if (b) {
			encoded[0] = 1;
		} else {
			encoded[0] = 0;
		}

		return encoded;
	}

	public static boolean unmarshallBoolean(byte[] data) {

		return (data[0] > 0);

	}

	public static byte[] marshallInteger(int x) {

		byte[] encoded = null;

		encoded = new byte[4];
		encoded[0] = (byte) ((x) >> 24);
		encoded[1] = (byte) ((x) >> 16);
		encoded[2] = (byte) ((x) >> 8);
		encoded[3] = (byte) ((x) >> 0);

		return encoded;
	}

	public static int unmarshallInteger(byte[] data) {

		int decoded = 0;

		decoded =  data[0] << 24 | (data[1] & 0xFF) << 16 | (data[2] & 0xFF) << 8 | (data[3] & 0xFF);

		return decoded;

	}
}
