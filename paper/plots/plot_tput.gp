#set term postscript eps color enhanced size 5,2.5 16 solid
set term pdf size 5,2.5 fsize 10 linewidth 1

set title 'Spark Throughput 1 host' font "Times-Roman, 14"
set xlabel 'Time'
set ylabel 'Tuples/sec'

load 'ggplot.gp'
load 'brewer-set1.gp'

#set logscale y
#set mytics default

set key inside left top
set xrange [0:600]

set output 'sp1_tput.pdf'

plot "< grep 'Throughput: ' data/log_sp1.txt | sed 's/Throughput: //g'" w l lw 6 t "Processing Tput", \
"data/sent_tputs_sp1_extend.txt" using 0:2 w l lw 6 t "Sending Tput"

