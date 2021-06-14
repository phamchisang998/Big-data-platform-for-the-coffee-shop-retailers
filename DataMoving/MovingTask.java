package movingtask;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class MovingTask extends TimerTask {

	String src = "";
	String dest = "";
	int num = 0;
	int time = 0;
	
	public MovingTask(String s, String d, int n, int t) 
	{
		src = s;
		dest = d;
		num = n;
		time = t;
		
	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(this, new Date(), time);
	}

	public void run()
	{
		System.out.println("---------------------------");
		System.out.println("Receing data from shops ...");
		moveFiles(src, dest, num);
		System.out.println(Integer.toString(num) + " received successfully !");
		System.out.println("---------------------------");
	}   


	private static void moveFile(String src, String dest) 
	{
	    Path result = null;
	      
	    try 
	    {
	    	result = Files.move(Paths.get(src), Paths.get(dest));
	    } 
	    catch (IOException e) 
	    {
	    	System.out.println("Exception while moving file: " + e.getMessage());
	    }
	      
	    if (result != null) 
	    {
	    	System.out.println("  - Successfully.");
	    }
	    else
	    {
	    	System.out.println("  - Failed.");
	    }
	}
	

	private static void moveFiles(String src, String dest, int Num) // 
	{
	    // Creating a File object for directory
	    File directoryPath = new File(src);
	    
	    // Creating filter for directories files, to select files only
	    FileFilter fileFilter = new FileFilter(){
	         public boolean accept(File dir) 
	         {          
	            if (dir.isFile()) 
	            {
	               return true;
	            } 
	            else 
	            {
	               return false;
	            }
	         }
	    };        
	    
	    // Get list of files in the source folder
	    File[] list = directoryPath.listFiles(fileFilter);
	    
	    int i = 0;
	    
	    // Move Num file to destination folder
	    while ((i < Num) && (i < list.length))
	    {
	    	String srcFileName = src + "/" + list[i].getName();
	    	String destFileName = dest + "/" + list[i].getName();
	    	moveFile(srcFileName, destFileName);
	    	i++;	    	
	    }         
	}		
}
