set terminal pngcairo size 800,600 enhanced font 'Verdana,10'
set output 'tput.png'

#wait_time=system("echo $wait_time")

set title "Network Word Count Throughput, wait-time=" #wait_time
set ylabel "Tuples/sec"
set xlabel "Time"
unset key

plot 'tput.dat' w lines


