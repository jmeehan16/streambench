Streaming Database Benchmarks

tcp-generator: basic tcp-based tuple generation

stream_generator: previous project that generates tcp-based tuples

Startup Order:
streamcatcher.py
	python dumbstreamcatcher.py -s 'localhost' -p 3333 -o 'catcher_data.txt'

tupleGeneratorServerTime.py

	python tupleGeneratorServerRampUp.py -i 'sample1000.txt' -s 'localhost' -p 9999 --lower_tput 100 --upper_tput 500 --step_size 100 --ramp_window 30


OR	./run-example org.apache.spark.streaming.examples.StreamBenchRawTextSender 9999 /home/john/git/streambench/tcp-generat/sample-500words.txt 1 100


Spark!
	./run-example org.apache.spark.streaming.examples.StreamBenchWordCountAgg local[2] localhost 9999 1

Storm!
	First create the jar file of the project.  Then, from the root Storm directory, use:
	bin/storm jar teststorm.jar storm.starter.StreamBenchWordCountTopology stream1 localhost 9999

Streambase!
	sbd Wordcount.sbapp

------------------------------
Spark Notes
------------------------------
For Spark, the output is fairly straightforward

The benchmarks that we are using are StreamBenchWordCountAgg.scala and NetworkGrep.scala
I know those names are dumb.

The arguments are:
StreamBenchWordCountAgg <master> <hostname> <port> <numStreams>
NetworkGrep <master> <host> <port> <batchSeconds> <numStreams>

The output is sent to stdout and needs to be piped into a file.

When Spark "vomits" due to too much data, the throughput spikes all over the place.  I believe it also sometimes spits out error messages, though these are difficult to catch.  If it is truly overwhelmed, it will simply stop processing anything at all and crash.  As far as I've seen, Spark will not drop tuples.

------------------------------
Streambase Notes
------------------------------

We are using Wordcount.sbapp and Grep.sbapp.  Both of these can be run directly from their folder in git.

For Streambase, the output format is:
Throughput(actual):Minimum Timestamp:Ending Timestamp:Latency

The "tuple catcher" is required for Streambase, and it must be listening on port 3333.  dumbstreamcatcher.py is the recommended catcher.  It will send its output to stdout, so that needs to be piped into a file.

IMPORTANT: Make sure that the file you're feeding into the application has NO unicode.  I would recommend creating all sample files from sample1000.txt in the tcp-generator folder.

When Streambase "vomits," the sbd application will start throwing errors left and right.  It might not immediately be noticeable, but Streambase WILL drop tuples.  The latency might also  rise by a second or two when it is under heavy load.  If it is truly overwhelmed, it will likely just break.
