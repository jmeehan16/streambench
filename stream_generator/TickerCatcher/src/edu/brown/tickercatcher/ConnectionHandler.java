package edu.brown.tickercatcher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.Runnable;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
	private Socket socket;
	
	public ConnectionHandler(Socket socket) {
		this.socket = socket;
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while(true)
			{
				String message = (String) ois.readObject();
				System.out.println("Message Received: " + message);
			}
			//ois.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
