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
    wait_time_sec = args.wait_time / 1000.0
    print "Sending tuples for a total of %d seconds, with a wait time of %0.4f ms" % (args.test_time, args.wait_time)

    catcherSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    catcherSocket.connect((args.catcher_host, 3333))
    print 'Connected to StreamBench Catcher'

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((args.server_host, args.server_port))
    s.listen(1)

    conn, addr = s.accept()
    print 'Connected to Spark:', addr

    i = 0
    start = time.time()
    now = start
    while now - start < args.test_time:
        time.sleep(wait_time_sec) 
        now = time.time()
        ts = "TS-%d" % int(now*1000)
        conn.send("%s %s" % (ts, dataset[i % len(dataset)]))
        sent_tuples.append(ts)
        i += 1

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
    parser.add_argument('-i','--input', help='Input file name for tuple data', required=True)
    parser.add_argument('-s','--server_host', help='Address of SDMBS to send tuples to', required=False, default='localhost')
    parser.add_argument('-p','--server_port', help='Port of SDMBS to send tuples to', required=False, default=9999, type=int)
    parser.add_argument('-w','--wait_time', help='The time in milliseconds to sleep between each tuple', required=True, type=float)
    parser.add_argument('-t','--test_time', help='The total time in seconds for the test to run', required=True, type=int, default=30)
    parser.add_argument('-c','--catcher_host', help='The hostname for the catcher program', required=True, type=str, default="localhost")
    args = parser.parse_args()
 
    main(args)

