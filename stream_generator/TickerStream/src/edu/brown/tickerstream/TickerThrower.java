package edu.brown.tickerstream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Date;

public class TickerThrower {
	private static int portNum = 2001;
	private static Random r;
	
	private static String sendRandTicker()
	{
		String symbol;
		long pricingdate;
		int price;
		int p = r.nextInt();
		Date date = new Date();
		
		if(p < 0.4){
			symbol = "MSFT";
		}
		else if(p < 0.65){
			symbol = "GOOG";
		}
		else {
			symbol = "IBM";
		}
		//price = p * 1000.0;
		pricingdate = date.getTime();
		
		return symbol + ";" + pricingdate;			
	}
	
	public static void main(String[] args) {
		
		int waittime = 0;
		if (args.length > 0)
		{
			waittime = Integer.parseInt(args[0]);
		}
		
		r = new Random();
		
		try {
			InetAddress host = InetAddress.getLocalHost();
			Socket socket = new Socket(host.getHostName(), portNum);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			Thread.sleep(5000);
			while(true){
				oos.writeObject(sendRandTicker());
				Thread.sleep(waittime);
			}
			
			//oos.close();
		}
		catch (UnknownHostException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace(); 
		}
	}
}