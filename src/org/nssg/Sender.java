package org.nssg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Sender {


   public static void main(String[] args) throws Exception{
	   System.out.println("Start service>>>");
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])),"UTF-8"));
	 	String line = null;
	 	Map<String,Set<String>> data = new HashMap<String,Set<String>>();
	 	while((line = br.readLine()) != null) {
	 		String time = line.split(",")[0];
	 		if(data.containsKey(time)) {
	 			data.get(time).add(line);
	 		}else {
	 			Set<String> d = new HashSet<String>();
	 			d.add(line);
	 			data.put(time, d);
	 		}
	 	}
	 	br.close();
	 	
	 	System.out.println("Data loading completed!");
	    ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[1]));
	    while(true) {
	    	Socket socket = serverSocket.accept();
	 	new SocketThread(socket,data).start(); 
	    }
   }
}
