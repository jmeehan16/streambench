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

import org.apache.spark.util.IntParam
import org.apache.spark.storage.StorageLevel

import org.apache.spark.streaming._
import org.apache.spark.streaming.util.RawTextHelper

import java.net._
import java.io._
import scala.io._

/**
 * Receives text from multiple rawNetworkStreams and counts how many '\n' delimited
 * lines have the word 'the' in them. This is useful for benchmarking purposes. This
 * will only work with spark.streaming.util.RawTextSender running on all worker nodes
 * and with Spark using Kryo serialization (set Java property "spark.serializer" to
 * "org.apache.spark.serializer.KryoSerializer").
 * Usage: RawNetworkGrep <master> <numStreams> <host> <port> <batchMillis>
 *   <master> is the Spark master URL
 *   <host> is "localhost".
 *   <port> is the port on which RawTextSender is running in the worker nodes.
 *   <batchMillise> is the Spark Streaming batch duration in milliseconds.
 *
 * `$ ./run-example org.apache.spark.streaming.examples.NetworkGrep local[2] localhost 9999 1`
 */

object NetworkGrep {
  def sendCounts(x: Array[String], out: PrintStream)
  {
    out.println("Timestamp: " + System.currentTimeMillis);
    for(i <- 0 until x.length) {
      out.println(x(i));
    }
    out.flush();
  }

  def main(args: Array[String]) {
    if (args.length != 4) {
      System.err.println("Usage: NetworkGrep <master> <host> <port> <batchSeconds>")
      System.exit(1)
    }

    val Array(master, host, IntParam(port), IntParam(batchMillis)) = args

    // Create the context with batch size of <batchSeconds>
    val ssc = new StreamingContext(master, "NetworkGrep", Seconds(batchMillis),
      System.getenv("SPARK_HOME"), Seq(System.getenv("SPARK_EXAMPLES_JAR")))
    val ia = InetAddress.getByName(host);

    // Create a NetworkInputDStream on target ip:port and count the
    // words in input stream of \n delimited test (eg. generated by 'nc') 
    val lines = ssc.socketTextStream(host, port)
    // JOHN: added this for output
    val socket = new Socket(ia, 3333)
    lazy val in = new BufferedSource(socket.getInputStream()).getLines()
    val out = new PrintStream(socket.getOutputStream())

    val words = lines.filter(_.contains(" the ")).foreach(r =>
      sendCounts(r.collect(), out))

    ssc.start()
  }
}