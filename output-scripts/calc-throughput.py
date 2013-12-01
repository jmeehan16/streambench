#!/usr/bin/python
import sys
import math

with open(sys.argv[1]) as f:
	lines = f.readlines()

timestamp = 0
throughput = 0
minLatency = 9999999

totalLatency = 0
totalThroughput = 0
numRuns = 0
if len(sys.argv) < 3:
	timePerRun = 1
else:
	timePerRun = int(sys.argv[2])

print "Reporting interval: %d sec(s)" % timePerRun

for line in lines:
	if line[:10] == "Timestamp:":
		if minLatency < 9999999:
			print "TIMESTAMP: " + str(timestamp)
			print "  LATENCY: " + str(minLatency)
			print "  THROUGHPUT: " + str(throughput)
			totalLatency = totalLatency + minLatency
			totalThroughput = totalThroughput + throughput
			numRuns = numRuns + 1
			minLatency = 9999999
			throughput = 0
 		#timestamp = (int(line[11:].replace("\n", ""))/1000) * 1000
		timestamp = int(line[11:].strip())
	else:
		line = line.replace("(","")
		line = line.replace(")","")
		ts = line.split(",")[0]
		if ts[:3] == "TS-":
			ts = ts.replace("TS-","")
			latency = int(timestamp) - int(ts)
			if latency < minLatency:
				minLatency = latency
			throughput=throughput+1

print "-------------------------"
print "Number of Runs: " + str(numRuns) + " runs"
print "Time Per Run: " + str(timePerRun) + " s"
print "AVG Latency: " + str(float(totalLatency)/numRuns) + " ms"
print "AVG Throughput: " + str(float(totalThroughput)/(numRuns*timePerRun)) + " tuples/sec"

