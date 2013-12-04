set terminal pngcairo size 800,600 enhanced font 'Verdana,10'
set output 'tput.png'

wait_time=system("echo $wait_time")
test_time=system("echo $test_time")

set title "Network Word Count Throughput\n wait-time=".wait_time." (ms), test-time=".test_time." (s)"
set ylabel "Tuples/sec"
set xlabel "Reporting Window Number"
unset key

plot 'tput.dat' w lines


