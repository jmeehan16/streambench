#!/bin/bash
echo "Summary results"
python calc-throughput.py catcher_data.txt | grep -A 10 "\-\-"
python calc-throughput.py catcher_data.txt | grep "  LATENCY:" | sed 's/  LATENCY: //g' > min_latency.dat

export wait_time=`grep "SENTINEL" catcher_data.txt | cut -d' ' -f2`
export test_time=`grep "SENTINEL" catcher_data.txt | cut -d' ' -f3`

gnuplot plot_min_latency.gp
echo "-------------------------"
echo "created min_latency.png"
