package movingtask;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;

public class FileMoving {

	public static void main(String[] args) 
	{
		String srcPath = "/home/hduser/data";
		String destPath = "/home/hduser/realtime-data";
		int N = 2;
		int time = 10000;
		
		for(int index =0 ; index < args.length -1; index ++){
			String item = args[index];
			switch(item){
				case "-n":
					N = Integer.parseInt(args[index+1]);
					break;
				case "-i":
					srcPath = args[index+1];
					break;
				case "-o":
					destPath = args[index+1];
					break;
				case "-t":
					time = Integer.parseInt(args[index+1]) * 1000;
					break;
				default:
					break;

			}
		}
			
		// Check if folder exists, if not create it.
		File theDir = new File(destPath);
		
		if (!theDir.exists())
		{
		    theDir.mkdirs();
		}
		
		new MovingTask(srcPath, destPath, N, time);

	}
	
		
}
