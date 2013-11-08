import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Date;

public class TickerThrower {
	private static int portNum = 2001; //21212;
	private static Random r;
	
	private static String sendRandTicker()
	{
		String symbol;
		long pricingdate;
		double price;
		double p = r.nextDouble();
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
		price = p * 1000.0;
		pricingdate = date.getTime();
		
		return symbol + ";" + pricingdate + ";" + price;			
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
			//System.out.println("Host: " + host);
			//System.out.println("Host Name: " + host.getHostName());
			Socket socket = new Socket(host.getHostName(), portNum);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			
			while(true){
				oos.writeObject(sendRandTicker());
				Thread.sleep(waittime);
			}
			
			//oos.writeObject("ant hstore-invoke -Dproject=tickertest -Dproc=InsertTickerRow -Dparam0='AAA' -Dparam1=12345 -Dparam2=5");
			
			//oos.close();
		}
		catch (UnknownHostException e){
			System.err.println("ERROR 1");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.err.println("ERROR 2");
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			System.err.println("ERROR 3");
			e.printStackTrace(); 
		}
	}
}
