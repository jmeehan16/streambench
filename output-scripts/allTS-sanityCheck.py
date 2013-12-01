#!/usr/bin/python
import sys
import math

with open(sys.argv[1]) as f:
  lines = f.readlines()

with open(sys.argv[2]) as f2:
  output = f.readlines()

found = False

for line in lines:
  for o in output:
  	o = o.replace("(","")
	o = o.replace(")","")
	ts = line.split(",")[0]
	if ts == line:
	  found = True
	  break
  if found == False:
  	print line
  found = False

