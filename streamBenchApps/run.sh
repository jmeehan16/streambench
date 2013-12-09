#!/bin/bash

cd ${HOME}/streambench/streamBenchApps

master="ec2-54-221-150-190.compute-1.amazonaws.com"
sdms_gen=""
${HOME}/spark/sbt/sbt "run spark://${master}:7077 {$sdms_gen} 9999"

