"""
Tuple: 'TS1384902645 ID12345 this is a sentence'

"""

import socket
import argparse
import time
import calc_parameters as cp

sent_tuples = []

def main(args):
    fd = open(args.input, 'r')
    dataset = fd.readlines()
    #print "Sending tuples for a total of %d seconds, with a wait time of %0.4f ms" % (args.test_time, args.wait_time)

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((args.server_host, args.server_port))
    s.listen(1)

    conn, addr = s.accept()
    print 'Connected to SDMS:', addr

    i = 0
    for tput in xrange(args.lower_tput, args.upper_tput, args.step_size):
        start = time.time()
        now = start
        window_sent = 0
        p = cp.calc_parameters(tput)
        wait_time = p['wait_time'] / 1000.0 #convert ms->s
        batch_size = p['batch_size']
        window_sent = 0
        print "Sending %d tuples/sec (wt=%0.2f, bs=%d)" % (tput, wait_time, batch_size)
        while now - start < args.ramp_window:
            time.sleep(wait_time)
            now = time.time()
            for k in xrange(batch_size):
                ts = "TS-%d" % int(now*1000)
                conn.send("%s %s" % (ts, dataset[i % len(dataset)]))
                sent_tuples.append(ts)
                i += 1
                window_sent += 1
        print "Average Sending Throughput %0.4f tuples/sec" % (window_sent / (now-start))

    conn.close()
    s.close()

    fd = open('sent_tuples.txt', 'w')
    for st in sent_tuples:
        fd.write(st + "\n")
    fd.close()

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Read in a tuple data file and send them to a SDBMS')
    parser.add_argument('-i', '--input', help='Input file name for tuple data', required=True)
    parser.add_argument('-s', '--server_host', help='Address of SDMBS to send tuples to', required=False, default='localhost')
    parser.add_argument('-p', '--server_port', help='Port of SDMBS to send tuples to', required=False, default=9999, type=int)
    parser.add_argument('-lt', '--lower_tput', help='Starting lower-bound throughput', required=True, type=int)
    parser.add_argument('-ut', '--upper_tput', help='Final upper-bound throughput', required=True, type=int)
    parser.add_argument('-ss', '--step_size', help='Step size of increasing throughput', required=True, type=int)
    parser.add_argument('-rw', '--ramp_window', help='Time in seconds of each ramp window', required=True, type=int)
    args = parser.parse_args()
    
    main(args)

