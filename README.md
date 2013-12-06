Streaming Database Benchmarks

tcp-generator: basic tcp-based tuple generation

stream_generator: previous project that generates tcp-based tuples

Startup Order:
streamcatcher.py
	python streamcatcher.py -s 'localhost' -p 3333 -o 'catcher_data.txt'

tupleGeneratorServerTime.py
	python tupleGeneratorServerTime.py -i 'samples.txt' -s 'localhost' -p 9999 -w 10 -t 30 -c 'localhost'

Spark!
	./run-example org.apache.spark.streaming.examples.StreamBenchWordCount local[2] localhost 9999


