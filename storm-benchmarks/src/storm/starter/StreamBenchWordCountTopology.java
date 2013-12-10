package storm.starter;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.ShellBolt;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import storm.starter.bolt.PrinterBolt;
import storm.starter.bolt.RollingCountBolt;
import storm.starter.spout2.RandomSentenceSpout;
import storm.starter.spout2.SentenceCatcherSpout;

import java.util.HashMap;
import java.util.Map;


/**
 * This topology demonstrates Storm's stream groupings and multilang capabilities.
 */
public class StreamBenchWordCountTopology {
	public static class SplitSentence implements IRichBolt {
		OutputCollector _collector;
		
		public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		   _collector = collector;
		}
 
		public void execute(Tuple tuple) {
			String sentence = tuple.getString(0);
			for(String word: sentence.split(" ")) {
				_collector.emit(tuple, new Values(word));
			}
			_collector.ack(tuple);
		}
	
	    public void cleanup() {
	    }
	
	    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	    	declarer.declare(new Fields("word"));
	    }
	    
	    @Override
	    public Map<String, Object> getComponentConfiguration() {
	        return null;
	    }
	}

  public static class WordCount extends BaseBasicBolt {
    Map<String, Integer> counts = new HashMap<String, Integer>();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
      String word = tuple.getString(0);
      Integer count = counts.get(word);
      if (count == null)
        count = 0;
      count++;
      counts.put(word, count);
      collector.emit(new Values(word, count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("word", "count"));
    }
  }

  public static void main(String[] args) throws Exception {
	
	
	if(args.length < 3) {
		System.err.println("Too few arguments!");
		return;
	}
	String host = args[1];
	int port = Integer.parseInt(args[2]);
	
	TopologyBuilder builder = new TopologyBuilder();
	
	builder.setSpout("spout", new SentenceCatcherSpout(host, port), 1);
	//builder.setSpout("spout", new RandomSentenceSpout(), 5);
	
	builder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");
	//RollingCountBolt(1 sec window, emit every 1 sec)
	//builder.setBolt("count", new WordCount()).fieldsGrouping("windowcount", new Fields("word"));
	builder.setBolt("windowcount", new RollingCountBolt(1, 1), 12).fieldsGrouping("split", new Fields("word"));
	builder.setBolt("print", new PrinterBolt()).shuffleGrouping("windowcount");
	
	Config conf = new Config();
	conf.setDebug(true);
	
	/**
	if (args != null && args.length > 0) {
		conf.setNumWorkers(3);
		
		StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
	}
	else {
	*/
		conf.setMaxTaskParallelism(3);
		
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("word-count", conf, builder.createTopology());
				
		Thread.sleep(10000);
				
		cluster.shutdown();
		//}
	}
}
