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

/**
 * Counts words in UTF8 encoded, '\n' delimited text received from the network every second.
 * Usage: NetworkWordCount <master> <hostname> <port>
 *   <master> is the Spark master URL. In local mode, <master> should be 'local[n]' with n > 1.
 *   <hostname> and <port> describe the TCP server that Spark Streaming would connect to receive data.
 *
 * To run this on your local machine, you need to first run a Netcat server
 *    `$ nc -lk 9999`
 * and then run the example
 *    `$ ./run-example org.apache.spark.streaming.examples.StreamBenchWordCount local[2] localhost 9999`
 */
object StreamBenchWordCount {
  

  def sendWords(x: Array[(java.lang.String, Int)], out: PrintStream)
  {
    out.println("Timestamp: " + System.currentTimeMillis);
    for(i <- 0 until x.length) {
      out.println(x(i));
    }
    out.flush();
  }

  def main(args: Array[String]) {
    if (args.length < 3) {
      System.err.println("Usage: NetworkWordCount2 <master> <hostname> <port>\n" +
        "In local mode, <master> should be 'local[n]' with n > 1")
      System.exit(1)
    }



    // Create the context with a 1 second batch size
    val ssc = new StreamingContext(args(0), "StreamBenchWordCount", Seconds(1),
      System.getenv("SPARK_HOME"), Seq(System.getenv("SPARK_EXAMPLES_JAR")))
    val ia = InetAddress.getByName("localhost");

    // Create a NetworkInputDStream on target ip:port and count the
    // words in input stream of \n delimited test (eg. generated by 'nc') 
    val lines = ssc.socketTextStream(args(1), args(2).toInt)
    // JOHN: added this for output
    val socket = new Socket(ia, 3333)
    lazy val in = new BufferedSource(socket.getInputStream()).getLines()
    val out = new PrintStream(socket.getOutputStream())

    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)

    wordCounts.foreach(x => sendWords(x.collect(),out))


    ssc.start()

  }
}
