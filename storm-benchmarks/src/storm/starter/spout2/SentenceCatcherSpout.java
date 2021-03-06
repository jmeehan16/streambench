package storm.starter.spout2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class SentenceCatcherSpout extends BaseRichSpout {
	  SpoutOutputCollector _collector;
	  String host;
	  int port;
	  transient Socket clientSocket;
	  transient BufferedReader in;
	  Random _rand;
			
	  public SentenceCatcherSpout(String host, int port){
		  super();
		  this.host = host;
		  this.port = port;
		  _rand = new Random();
		  System.out.println("CONSTRUCTOR");
		  
		  try {
			  System.out.println("CREATING SOCKET");
			  clientSocket = new Socket(host, port);
			  System.out.println("CREATING BUFFERED READER");
			  in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			  System.out.println("CREATED BUFFERED READER");
		  }
		  catch(IOException e) {
			  System.err.println("Unable to connect to " + host + ":" + port);
			  System.exit(-1);
		  }
		  System.out.println("in: " + in.toString());
	  }

	  @Override
	  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
	    _collector = collector;
	  }

	  @Override
	  public void nextTuple() {
		Utils.sleep(100);
		String sentence = "";
		//String[] sentences = new String[]{ "the cow jumped over the moon", "an apple a day keeps the doctor away",
		//        "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature" };
		//String sentence = sentences[_rand.nextInt(sentences.length)];
		if(in == null)
		{
			System.out.println("NULL IN");
			try {
				clientSocket = new Socket(host, port);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			assert(in != null);
			sentence = in.readLine();
		} catch (IOException e) {
			_collector.emit(new Values(""));
			//System.out.println("NEXT TUPLE BREAK");
			//e.printStackTrace();
		}

	    _collector.emit(new Values(sentence));
	  }

	  @Override
	  public void ack(Object id) {
	  }

	  @Override
	  public void fail(Object id) {
	  }

	  @Override
	  public void declareOutputFields(OutputFieldsDeclarer declarer) {
	    declarer.declare(new Fields("word"));
	  }

}