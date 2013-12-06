#!/usr/bin/env python
import socket
import threading
import sys

TCP_IP = 'localhost'
TCP_PORT = 3333
BUFFER_SIZE = 1024
MESSAGE = "Hello, World!"

fd = open('catcher_data.txt', 'w')

try:
  s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  s.bind((TCP_IP, TCP_PORT))
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

  fd.write(data)

s.close()
tupleGenConn.close()
fd.close()
