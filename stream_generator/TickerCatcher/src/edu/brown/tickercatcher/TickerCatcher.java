package edu.brown.tickercatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TickerCatcher {
	private int portNum = 2001;
	private ServerSocket server;	
	
	public TickerCatcher(){
		try{
			server = new ServerSocket(portNum);
		}
		catch (IOException e) {
			System.err.println("Could not listen on port " + portNum);
			System.exit(-1);
		}
	}
	
	public void handleConnection(){
		System.out.println("Waiting for client message...");
		
		while(true){
			try {
				Socket socket = server.accept();
				new ConnectionHandler(socket);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}