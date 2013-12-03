#!/bin/bash
echo "Summary results"
python calc-throughput.py catcher_data.txt | grep -A 10 "\-\-"
python calc-throughput.py catcher_data.txt | grep "THROUGHPUT" | sed 's/  THROUGHPUT: //g' > tput.dat

export wait_time=$1
gnuplot plot_tput.gp
echo "-------------------------"
echo "created tput.png"
