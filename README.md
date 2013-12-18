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
	./run-example org.apache.spark.streaming.examples.StreamBenchWordCount local[2] localhost 9999

Storm!
	First create the jar file of the project.  Then, from the root Storm directory, use:
	bin/storm jar teststorm.jar storm.starter.StreamBenchWordCountTopology stream1 localhost 9999

Streambase!
	sbd Wordcount.sbapp

