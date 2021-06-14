package salesofproducts;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class driver {
	public static void main(String[] args) throws Exception {
		Map<String,String> params = setUpInput(args);
		multipleFirstLayerJob(params.get("dt"),params.get("inputFolder"),"/temporary");
		createSecondLayerMapReduceJob(params.get("topK"),"/temporary",params.get("outputFolder"));
	}
	public static Map<String,String> setUpInput(String[] args){
		Map<String,String> output=new HashMap<String,String>(){
			{
				put("topK","all");
				put("dt","all");
				put("inputFolder","/input");
				put("outputFolder","/output");
			}
		};
		for(int index =0 ; index < args.length -1; index ++){
			String item = args[index];
			switch(item){
				case "-k":
					output.put("topK",args[index+1]);
					break;
				case "-i":
					output.put("inputFolder",args[index+1]);
					break;
				case "-o":
					output.put("outputFolder",args[index+1]);
					break;
				case "-d":
					output.put("dt",args[index+1]);
					break;
				default:
					break;

			}
		}
		return output;
	}
	public static List<String> getAllFilePath(Path filePath, FileSystem fs) throws FileNotFoundException, IOException {
		List<String> fileList = new ArrayList<String>();
		FileStatus[] fileStatus = fs.listStatus(filePath);
		for (FileStatus fileStat : fileStatus) {
			if (fileStat.isDirectory()) {
				fileList.addAll(getAllFilePath(fileStat.getPath(), fs));
			} else {
				fileList.add(fileStat.getPath().toString());
			}
		}
		return fileList;
	}

	public static void multipleFirstLayerJob (String dt,String inputFolder,String outputFolder) throws Exception{
		Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

		System.out.println(inputFolder);
		System.out.println(outputFolder);
		if(fs.exists(new Path(outputFolder))){
			fs.delete(new Path(outputFolder),true);
		}
		fs.mkdirs(new Path(outputFolder));

		List<String> filenames = getAllFilePath(new Path(inputFolder),fs);
		for(String inFileName : filenames){
			String name = inFileName.split(inputFolder)[1];
			String tempFolder = name.split("\\.")[0];

			
			createFirstLayerMapReduceJob(dt,inFileName,tempFolder);
			List<String> allOutputFilenames = getAllFilePath(new Path(tempFolder),fs);
			for(String outFileName: allOutputFilenames){
				if(outFileName.contains("part")){
					FileUtil.copy(fs,new Path(outFileName), fs, new Path(outputFolder+name),true,conf);
				}
			}
			if(fs.exists(new Path(tempFolder))){
				fs.delete(new Path(tempFolder),true);
			}
		}
	}

	public static void createFirstLayerMapReduceJob (String dt,String inputFile,String outputFolder) throws Exception{
		Configuration conf = new Configuration();
		conf.set("dt", dt);
		conf.set("mapred.textoutputformat.separator", ",");
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(salesofproducts.driver.class);
		job.setMapperClass(salesofproducts.mapper1.class);
		job.setReducerClass(salesofproducts.reducer1.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path(inputFile));
		FileOutputFormat.setOutputPath(job, new Path(outputFolder));

		if (!job.waitForCompletion(true))
			return;
	}
	public static void createSecondLayerMapReduceJob(String topK, String inputFolder,String outputFolder) throws Exception{
		Configuration conf = new Configuration();
		conf.set("topK", topK);
		conf.set("mapred.textoutputformat.separator", ",");
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(salesofproducts.driver.class);
		job.setMapperClass(salesofproducts.mapper2.class);
		job.setReducerClass(salesofproducts.reducer2.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)

		FileSystem fs = FileSystem.get(conf);

		FileInputFormat.setInputPaths(job, new Path(inputFolder));
		FileOutputFormat.setOutputPath(job, new Path(outputFolder));

		if (!job.waitForCompletion(true)){
			fs.delete(new Path(inputFolder),true);
			return;
		}

	}

}
