import math

def calc_parameters(tput):
    b = calc_batch_size(tput)
    wt = calc_wait_time(tput, b)
    return {'batch_size':b,'wait_time':wt}

def calc_batch_size(tput):
    return int(math.floor(tput / 10000.0) + 1)

def calc_wait_time(tput, b):
    return b * (1000.0 / tput) 
