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

import java.nio.ByteBuffer
import org.apache.spark.util.{RateLimitedOutputStream, IntParam}
import java.net.ServerSocket
import org.apache.spark.{Logging}
import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream
import scala.io._
import java.io._
import java.net._
import org.apache.spark.serializer.KryoSerializer
import java.lang.Double

/**
 * A helper program that sends blocks of Kryo-serialized text strings out on a socket at a
 * specified rate. Used to feed data into RawInputDStream.
 *
 * ./run-example org.apache.spark.streaming.examples.StreamBenchRawTextSender 9999 "../git/streambench/tcp-generator/sample.txt" 10`
 */
object StreamBenchRawTextSender extends Logging {
  def main(args: Array[String]) {
    if (args.length != 4) {
      System.err.println("Usage: StreamBenchRawTextSender <port> <file> <waitTime> <runsPerWait>")
      System.exit(1)
    }
    // Parse the arguments using a pattern match
    val Array(IntParam(port), file, IntParam(waitTime), IntParam(runsPerWait)) = args

    // Repeat the input data multiple times to fill in a buffer
    val lines = Source.fromFile(file).getLines().toArray
    //val bufferStream = new FastByteArrayOutputStream(blockSize + 1000)
    //val ser = new KryoSerializer().newInstance()
    //val serStream = ser.serializeStream(bufferStream)
    //var i = 0

    val serverSocket = new ServerSocket(port)
    logInfo("Listening on port " + port)

    
    
    val socket = serverSocket.accept()
    var runs = 0
    val out = new PrintStream(socket.getOutputStream())
    while(true) {
      try {
        for (line <- lines) {
          out.println("TS" + System.currentTimeMillis + " " + line)
          //System.out.println("TS" + System.currentTimeMillis + " " + line)
          out.flush();
          runs= runs + 1;
          if(runs%runsPerWait == 0) {
            Thread.sleep(waitTime)
          }
        }
      } catch {
        case e: IOException =>
          logError("Client disconnected")
          socket.close()
      }
    }
  }
}
