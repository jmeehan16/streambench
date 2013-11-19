import socket
import string
import random

TCP_IP='localhost'
TCP_PORT=9999

data = [random.choice(string.letters) for i in range(100)] 

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((TCP_IP, TCP_PORT))
s.listen(1)

conn, addr = s.accept()
print 'Connection address:', addr

for i in range(100):
  conn.send(data[i] + "\n")

conn.close()
s.close()
