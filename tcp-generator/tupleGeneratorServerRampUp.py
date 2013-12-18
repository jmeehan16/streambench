"""
Tuple: 'TS1384902645 ID12345 this is a sentence'

"""

import socket
import argparse
import time

sent_tuples = []

def main(args):
    fd = open(args.input, 'r')
    dataset = fd.readlines()
    #print "Sending tuples for a total of %d seconds, with a wait time of %0.4f ms" % (args.test_time, args.wait_time)

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((args.server_host, args.server_port))
    s.listen(1)

    conn, addr = s.accept()
    print 'Connected to Spark:', addr

    # Starting wait time.
    wait_time_sec = args.wait_time / 1000.0 #convert ms->s
    wait_time_decrease = args.step_decrease / 1000.0 #convert ms->s
    i = 0 #total tuples sent
    ramp_num = 0
    while ramp_num < args.total_ramps:
        start = time.time()
        now = start
        window_sent = 0
        while now - start < args.window_time:
            time.sleep(wait_time_sec) 
            now = time.time()
            for k in xrange(args.burst_size):
                ts = "TS-%d" % int(now*1000)
                conn.send("%s %s" % (ts, dataset[i % len(dataset)]))
                sent_tuples.append(ts)
                i += 1
                window_sent += 1
        print "Throughput %0.4f tuples/sec" % (window_sent / (now-start))
        wait_time_sec -= wait_time_decrease
        ramp_num += 1
        print "Starting new window (%d), ramping up throughput!" % ramp_num

    conn.close()
    s.close()

    fd = open('sent_tuples.txt', 'w')
    for st in sent_tuples:
        fd.write(st + "\n")
    fd.close()

    start = int(sent_tuples[0].replace("TS-", ""))
    end = int(sent_tuples[-1].replace("TS-", ""))
    total = len(sent_tuples)

    # Send test arguments and sentinel to StreamBench catcher
    print "Sending sentinel!"
    catcherSocket.send("SENTINEL %.4f %d\n" % (args.wait_time, args.test_time))
    catcherSocket.close()

    print "Average throughput: %.4f" % (float(total) / (end-start)*1000)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Read in a tuple data file and send them to a SDBMS')
    parser.add_argument('-i', '--input', help='Input file name for tuple data', required=True)
    parser.add_argument('-s', '--server_host', help='Address of SDMBS to send tuples to', required=False, default='localhost')
    parser.add_argument('-p', '--server_port', help='Port of SDMBS to send tuples to', required=False, default=9999, type=int)
    parser.add_argument('-w', '--window_time', help='Window time (sec) to wait between ramp ups', required=True, type=int, default=30)
    parser.add_argument('-d', '--step_decrease', help='The time (milliseconds) to decrease the wait_time between sends', \
            required=True, type=float)
    parser.add_argument('-w', '--wait_time', help='The starting time in milliseconds to sleep between sending each tuple group', \
            required=True, type=float, default=10)
    parser.add_argument('-tr', '--total_ramps', help='', required=True, type=int)
    parser.add_argument('-b', '--burst_size', help='', required=False, type=int, default=10)
    args = parser.parse_args()
    
    main(args)

