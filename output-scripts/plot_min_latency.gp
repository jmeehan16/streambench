set terminal pngcairo size 800,600 enhanced font 'Verdana,10'
set output 'min_latency.png'

#wait_time=system("echo $wait_time")

set title "Network Word Count Min Latency, wait-time=" #wait_time
set ylabel "ms"
set xlabel "Time"
unset key

plot 'min_latency.dat' w lines


