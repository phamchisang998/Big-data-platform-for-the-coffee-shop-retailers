A program written in Java, named movingtask.jar with parameters:
-n <number of files>
-t <time> (in seconds).
-i <input directory> (default /home/hduser/data)
-o <output directory> (default /home/hduser/realtime-data)
Example: java –jar movingtask.jar –n 1440 –t 1 –i /home/hduser/data –o /home/hduser/realtime-data
Every 1 second will move 1,440 files from /home/hduser/data to /home/hduser/realtime-data.