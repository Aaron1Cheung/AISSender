package org.nssg;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SocketThread extends Thread {
	Socket csocket;
	Map<String,Set<String>> data;
	public SocketThread(Socket cs,Map<String,Set<String>> data) {
		this.csocket = cs;
		this.data = data;
	}
	public void run() {
		System.out.println(new Date().toString()+"\t"+"New connection accepted "+
		 	      csocket.getInetAddress()+":"+csocket.getPort());
		OutputStream os = null;
		try {
			os = csocket.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedOutputStream bo = new BufferedOutputStream(os);
	 	Set<String> keys = new TreeSet<String>(data.keySet());
	 	while(true) {
	 		for(String i:keys) {
	 		Set<String> s = data.get(i);
	 		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		String info = "";
	 		for(String ii:s) {
	 			info += ii+"\n";
	 		}
	 		try {
				bo.write(info.getBytes());
			} catch (IOException e) {
				try {
					csocket.close();
					System.out.println(new Date().toString()+"\t"+"The link is broken "+
					 	      csocket.getInetAddress()+":"+csocket.getPort());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
	 		}
	 	}
	 	
	 	
	}
}
