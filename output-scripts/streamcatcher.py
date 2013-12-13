#!/usr/bin/env python
import socket
import threading
import sys
import argparse

TCP_IP = 'localhost'
TCP_PORT = 3333
BUFFER_SIZE = 1024
MESSAGE = "Hello, World!"

def main(args):

  fd = open(args.output_file, 'w')

  try:
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((args.server_host, args.server_port))
    s.listen(5)
  except socket.error:
    print 'Failed to create socket'
    sys.exit()

  tupleGenConn, tupleGenAddr = s.accept()
  print "Connection to tuple generator:", tupleGenAddr
  tupleGenConn.setblocking(0) #Set non-blocking

  conn, addr = s.accept()
  print "Spark Application address:", addr
  conn.setblocking(2)

  while True:
    try:
      tupleGenData = tupleGenConn.recv(BUFFER_SIZE)
      print "Completed test! Quitting..."
      fd.write("\n" + tupleGenData)
      print "Test run args: %s" % tupleGenData.strip()
      break
    except socket.error:
      pass

    data = conn.recv(BUFFER_SIZE)
    if not data:
      continue
    
    print data
    fd.write(data)

  s.close()
  tupleGenConn.close()
  fd.close()

if __name__ == '__main__':
  parser = argparse.ArgumentParser(description='Read in a tuple data file and send them to a SDBMS')
  parser.add_argument('-s','--server_host', help='Address of SDMBS to send tuples to', required=False, default='localhost')
  parser.add_argument('-p','--server_port', help='Port of SDMBS to send tuples to', required=False, default=3333, type=int)
  parser.add_argument('-o','--output_file', help='File to output results to', required=False, default='catcher_data.txt', type=str)

args = parser.parse_args()
main(args)
