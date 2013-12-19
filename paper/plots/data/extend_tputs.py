
fd = open('sent_tputs_sp1.txt', 'r')
lines = fd.readlines()
fd.close()

fd = open('sent_tputs_sp1_extend.txt', 'w')
for line in lines:
    for i in range(10):
        fd.write(line)

fd.close()
