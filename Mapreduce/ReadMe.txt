hadoop jar <programName>.jar [Option]
-i: input folder [default /input]
-o: output folder [default /output]
-k: top k [default take all elements]
-d: date time [default take all date time].

hadoop jar topkproducts.jar -i /data -o output -k 5 -d 03-2020
