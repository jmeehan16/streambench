#!/usr/bin/env python
import socket
import threading

TCP_IP = '127.0.0.1'
TCP_PORT = 3333
BUFFER_SIZE = 1024
MESSAGE = "Hello, World!"

try:
  s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  s.bind((TCP_IP, TCP_PORT))
  s.listen(5)
except socket.error:
  print 'Failed to create socket'
  sys.exit()

conn, addr = s.accept()
print 'Connection address:', addr

while 1:
    data = conn.recv(BUFFER_SIZE)
    if not data: continue
    print data
s.close()