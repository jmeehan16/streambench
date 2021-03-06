/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.streaming.examples

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import java.net._
import java.io._
import scala.io._
import scala.math.min

/**
 * Counts words in UTF8 encoded, '\n' delimited text received from the network every second.
 * Usage: NetworkWordCount <master> <hostname> <port>
 *   <master> is the Spark master URL. In local mode, <master> should be 'local[n]' with n > 1.
 *   <hostname> and <port> describe the TCP server that Spark Streaming would connect to receive data.
 *
 * To run this on your local machine, you need to first run a Netcat server
 *    `$ nc -lk 9999`
 * and then run the exampl
 *    `$ ./run-example org.apache.spark.streaming.examples.StreamBenchWordCountAgg local[2] localhost 9999`
 */
object StreamBenchWordCountAgg {
  

  def sendWords(x: Array[(java.lang.String, Int)], out: PrintStream)
  {
    out.println("Timestamp: " + System.currentTimeMillis);
    for(i <- 0 until x.length) {
      out.println(x(i));
    }
    out.flush();
  }

  def main(args: Array[String]) {
    if (args.length < 4) {
      System.err.println("Usage: StreamBenchWordCountAgg <master> <hostname> <port> <numStreams>\n" +
        "In local mode, <master> should be 'local[n]' with n > 1")
      System.exit(1)
    }



    // Create the context with a 1 second batch size
    val ssc = new StreamingContext(args(0), "StreamBenchWordCountAgg", Seconds(1),
      System.getenv("SPARK_HOME"), Seq(System.getenv("SPARK_EXAMPLES_JAR")))

    val rawStreams = (1 to args(3).toInt).map(_ =>
      ssc.socketTextStream(args(1), args(2).toInt)).toArray
    val union = ssc.union(rawStreams)


    //val ia = InetAddress.getByName("localhost");

    // Create a NetworkInputDStream on target ip:port and count the
    // words in input stream of \n delimited test (eg. generated by 'nc') 
    ////////////val lines = ssc.socketTextStream(args(1), args(2).toInt)
    // JOHN: added this for output
    //val socket = new Socket(ia, 3333)
    //lazy val in = new BufferedSource(socket.getInputStream()).getLines()
    //val out = new PrintStream(socket.getOutputStream())

    val words = union.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    val tsTuples = words.filter(x => x contains "TS");
    val minTS = tsTuples.map(x => x.substring(3).toLong).reduce(min(_,_))
    val minLatency = minTS.map(x => "Latency: " + (System.currentTimeMillis - x))
    val minTSstr = minTS.map(x => "TS: " + x)
    val tupleCount = tsTuples.count().map(x => "Throughput: " + x)
    val output = minTSstr.union(minLatency)
    val output2 = output.union(tupleCount)
    //val numTuples = 
    
    //val avgLatency = tsTuples.foreach(x =>    System.currentTimeMillis)

    //wordCounts.foreach(x => sendWords(x.collect(),out))
    //tsTuples.count().print()
    output2.print()
    //wordCounts.foreach(x => calculateAgg(x)))
    
    //numTuples.reduce(_ + _).print()
    //System.out.println("Total: " + sum)

    ssc.start()

  }
}
