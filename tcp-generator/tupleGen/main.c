#include <stdio.h>
#include <sys/time.h>

int main()
{
    struct timespec ts;
    ts.tv_sec = 0;
    ts.tv_nsec = 1000;

    struct timeval start;
    struct timeval end;
    int i = 0;
    for(i=0; i<1000; i++) {
        gettimeofday(&start, NULL);
        nanosleep(&ts, NULL);
        gettimeofday(&end, NULL);
        printf("%d\n", end.tv_usec - start.tv_usec);
    }
}
