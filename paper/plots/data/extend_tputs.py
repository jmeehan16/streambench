
fd = open('sent_tputs_sb1.txt', 'r')
lines = fd.readlines()
fd.close()

fd = open('sent_tputs_sb1_extend.txt', 'w')
for line in lines:
    for i in range(10):
        fd.write(line)

fd.close()
