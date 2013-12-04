#!/bin/bash
TMP_OUT="tmpCatcher.dat"

echo "Summary results"
python calc-throughput.py catcher_data.txt > $TMP_OUT
grep -A 10 "\-\-" $TMP_OUT
grep "THROUGHPUT" $TMP_OUT | sed 's/  THROUGHPUT: //g' > tput.dat

export wait_time=`grep "SENTINEL" catcher_data.txt | cut -d' ' -f2`
export test_time=`grep "SENTINEL" catcher_data.txt | cut -d' ' -f3`

gnuplot plot_tput.gp
echo "-------------------------"
echo "created tput.png"
rm $TMP_OUT

