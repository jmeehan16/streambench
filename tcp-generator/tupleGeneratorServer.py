"""
Tuple: 'TS1384902645 ID12345 this is a sentence'

"""

import socket

TCP_IP='localhost'
TCP_PORT=9999

MULTIPLIER = 10

def main(args):
    fd = open(args.input, 'r')
    dataset = fd.readlines()
    wait_time_sec = float(args.wait_time) / 1000.0
    print "Sending a total of %d tuples" % len(dataset)*MULTIPLIER

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((args.server_host, args.server_port))
    s.listen(1)

    conn, addr = s.accept()
    print 'Connection address:', addr

    for i in xrange(len(dataset)*MULTIPLIER):
       time.sleep(wait_time_sec) 
       conn.send(dataset(i % len(dataset))

    conn.close()
    s.close()

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Read in a tuple data file and send them to a SDBMS')
    parser.add_argument('-i','--input', help='Input file name for tuple data', required=True)
    parser.add_argument('-s','--server_host', help='Address of SDMBS to send tuples to', required=False, default='localhost')
    parser.add_argument('-p','--server_port', help='Port of SDMBS to send tuples to', required=False, default=9999)
    parser.add_argument('-w','--wait_time', help='The time in milliseconds to sleep between each tuple', required=True, type=int)
    args = parser.parse_args()
 
    main(args)

