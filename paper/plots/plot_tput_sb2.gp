#set term postscript eps color enhanced size 5,2.5 16 solid
set term pdf size 5,2.5 fsize 10 linewidth 1

set title 'System-X Throughput, 1 host, Grep' font "Times-Roman, 14"
set xlabel 'Time'
set ylabel 'Tuples/sec'

load 'ggplot.gp'
load 'brewer-set1.gp'

#set logscale y
#set mytics default

set key outside center bottom
#set xrange [0:600]

set output 'sb2_tput.pdf'

set arrow from 68.707,graph(0,0) to 68.707,graph(1,1) nohead lw 1
set arrow from 68.71,graph(0,0) to 68.71,graph(1,1) nohead lw 1
set arrow from 69.752,graph(0,0) to 69.752,graph(1,1) nohead lw 1
set arrow from 70.818,graph(0,0) to 70.818,graph(1,1) nohead lw 1
set arrow from 71.888,graph(0,0) to 71.888,graph(1,1) nohead lw 1
set arrow from 72.971,graph(0,0) to 72.971,graph(1,1) nohead lw 1
set arrow from 74.061,graph(0,0) to 74.061,graph(1,1) nohead lw 1
set arrow from 75.148,graph(0,0) to 75.148,graph(1,1) nohead lw 1
set arrow from 76.225,graph(0,0) to 76.225,graph(1,1) nohead lw 1
set arrow from 77.315,graph(0,0) to 77.315,graph(1,1) nohead lw 1
set arrow from 78.284,graph(0,0) to 78.284,graph(1,1) nohead lw 1
set arrow from 78.416,graph(0,0) to 78.416,graph(1,1) nohead lw 1
set arrow from 79.359,graph(0,0) to 79.359,graph(1,1) nohead lw 1
set arrow from 80.28,graph(0,0) to 80.28,graph(1,1) nohead lw 1
set arrow from 81.211,graph(0,0) to 81.211,graph(1,1) nohead lw 1
set arrow from 82.14,graph(0,0) to 82.14,graph(1,1) nohead lw 1
set arrow from 83.07,graph(0,0) to 83.07,graph(1,1) nohead lw 1
set arrow from 84.007,graph(0,0) to 84.007,graph(1,1) nohead lw 1
set arrow from 84.936,graph(0,0) to 84.936,graph(1,1) nohead lw 1
set arrow from 85.868,graph(0,0) to 85.868,graph(1,1) nohead lw 1
set arrow from 86.794,graph(0,0) to 86.794,graph(1,1) nohead lw 1
set arrow from 87.726,graph(0,0) to 87.726,graph(1,1) nohead lw 1
set arrow from 88.641,graph(0,0) to 88.641,graph(1,1) nohead lw 1
set arrow from 89.494,graph(0,0) to 89.494,graph(1,1) nohead lw 1
set arrow from 90.347,graph(0,0) to 90.347,graph(1,1) nohead lw 1
set arrow from 91.2,graph(0,0) to 91.2,graph(1,1) nohead lw 1
set arrow from 92.053,graph(0,0) to 92.053,graph(1,1) nohead lw 1
set arrow from 92.91,graph(0,0) to 92.91,graph(1,1) nohead lw 1
set arrow from 93.77,graph(0,0) to 93.77,graph(1,1) nohead lw 1
set arrow from 94.624,graph(0,0) to 94.624,graph(1,1) nohead lw 1
set arrow from 95.484,graph(0,0) to 95.484,graph(1,1) nohead lw 1
set arrow from 96.344,graph(0,0) to 96.344,graph(1,1) nohead lw 1
set arrow from 97.2,graph(0,0) to 97.2,graph(1,1) nohead lw 1
set arrow from 98.053,graph(0,0) to 98.053,graph(1,1) nohead lw 1
set arrow from 98.868,graph(0,0) to 98.868,graph(1,1) nohead lw 1
set arrow from 99.65,graph(0,0) to 99.65,graph(1,1) nohead lw 1
set arrow from 100.431,graph(0,0) to 100.431,graph(1,1) nohead lw 1
set arrow from 101.211,graph(0,0) to 101.211,graph(1,1) nohead lw 1
set arrow from 101.988,graph(0,0) to 101.988,graph(1,1) nohead lw 1
set arrow from 102.772,graph(0,0) to 102.772,graph(1,1) nohead lw 1
set arrow from 103.564,graph(0,0) to 103.564,graph(1,1) nohead lw 1
set arrow from 104.347,graph(0,0) to 104.347,graph(1,1) nohead lw 1
set arrow from 105.14,graph(0,0) to 105.14,graph(1,1) nohead lw 1
set arrow from 105.933,graph(0,0) to 105.933,graph(1,1) nohead lw 1
set arrow from 106.72,graph(0,0) to 106.72,graph(1,1) nohead lw 1
set arrow from 107.503,graph(0,0) to 107.503,graph(1,1) nohead lw 1
set arrow from 108.283,graph(0,0) to 108.283,graph(1,1) nohead lw 1
set arrow from 109.021,graph(0,0) to 109.021,graph(1,1) nohead lw 1
set arrow from 109.745,graph(0,0) to 109.745,graph(1,1) nohead lw 1
set arrow from 110.47,graph(0,0) to 110.47,graph(1,1) nohead lw 1
set arrow from 111.2,graph(0,0) to 111.2,graph(1,1) nohead lw 1
set arrow from 111.932,graph(0,0) to 111.932,graph(1,1) nohead lw 1
set arrow from 112.664,graph(0,0) to 112.664,graph(1,1) nohead lw 1
set arrow from 113.399,graph(0,0) to 113.399,graph(1,1) nohead lw 1
set arrow from 114.125,graph(0,0) to 114.125,graph(1,1) nohead lw 1
set arrow from 114.856,graph(0,0) to 114.856,graph(1,1) nohead lw 1
set arrow from 115.59,graph(0,0) to 115.59,graph(1,1) nohead lw 1
set arrow from 116.323,graph(0,0) to 116.323,graph(1,1) nohead lw 1
set arrow from 117.053,graph(0,0) to 117.053,graph(1,1) nohead lw 1
set arrow from 117.786,graph(0,0) to 117.786,graph(1,1) nohead lw 1
set arrow from 118.507,graph(0,0) to 118.507,graph(1,1) nohead lw 1
set arrow from 119.162,graph(0,0) to 119.162,graph(1,1) nohead lw 1
set arrow from 119.284,graph(0,0) to 119.284,graph(1,1) nohead lw 1
set arrow from 119.813,graph(0,0) to 119.813,graph(1,1) nohead lw 1
set arrow from 120.329,graph(0,0) to 120.329,graph(1,1) nohead lw 1
set arrow from 120.332,graph(0,0) to 120.332,graph(1,1) nohead lw 1
set arrow from 120.345,graph(0,0) to 120.345,graph(1,1) nohead lw 1
set arrow from 120.36,graph(0,0) to 120.36,graph(1,1) nohead lw 1
set arrow from 120.364,graph(0,0) to 120.364,graph(1,1) nohead lw 1
set arrow from 120.367,graph(0,0) to 120.367,graph(1,1) nohead lw 1
set arrow from 120.383,graph(0,0) to 120.383,graph(1,1) nohead lw 1
set arrow from 120.386,graph(0,0) to 120.386,graph(1,1) nohead lw 1
set arrow from 120.4,graph(0,0) to 120.4,graph(1,1) nohead lw 1
set arrow from 120.403,graph(0,0) to 120.403,graph(1,1) nohead lw 1
set arrow from 120.415,graph(0,0) to 120.415,graph(1,1) nohead lw 1
set arrow from 120.46,graph(0,0) to 120.46,graph(1,1) nohead lw 1
set arrow from 120.474,graph(0,0) to 120.474,graph(1,1) nohead lw 1
set arrow from 121.098,graph(0,0) to 121.098,graph(1,1) nohead lw 1
set arrow from 121.753,graph(0,0) to 121.753,graph(1,1) nohead lw 1
set arrow from 122.409,graph(0,0) to 122.409,graph(1,1) nohead lw 1
set arrow from 123.059,graph(0,0) to 123.059,graph(1,1) nohead lw 1
set arrow from 123.717,graph(0,0) to 123.717,graph(1,1) nohead lw 1
set arrow from 124.37,graph(0,0) to 124.37,graph(1,1) nohead lw 1
set arrow from 124.986,graph(0,0) to 124.986,graph(1,1) nohead lw 1
set arrow from 125.027,graph(0,0) to 125.027,graph(1,1) nohead lw 1
set arrow from 125.686,graph(0,0) to 125.686,graph(1,1) nohead lw 1
set arrow from 126.343,graph(0,0) to 126.343,graph(1,1) nohead lw 1
set arrow from 127,graph(0,0) to 127,graph(1,1) nohead lw 1
set arrow from 127.653,graph(0,0) to 127.653,graph(1,1) nohead lw 1
set arrow from 128.313,graph(0,0) to 128.313,graph(1,1) nohead lw 1
set arrow from 128.939,graph(0,0) to 128.939,graph(1,1) nohead lw 1
set arrow from 129.554,graph(0,0) to 129.554,graph(1,1) nohead lw 1
set arrow from 130.17,graph(0,0) to 130.17,graph(1,1) nohead lw 1
set arrow from 130.784,graph(0,0) to 130.784,graph(1,1) nohead lw 1
set arrow from 131.4,graph(0,0) to 131.4,graph(1,1) nohead lw 1

plot "< sed '/^\s*$/d' data/results_sb2.txt" w l lw 6 t "Processing Tput", \
"data/sent_tputs_sb2.txt"  w l lw 6 t "Sending Tput"

