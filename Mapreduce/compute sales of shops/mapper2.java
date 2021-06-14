package salesofshops;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class mapper2 extends Mapper<LongWritable, Text, Text, LongWritable> {
	String dt;
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		String stmt = ivalue.toString();
		String[] arr= stmt.split(",");
		try {
			long val = Long.parseLong(arr[1]);
			String key = arr[0];
			context.write(new Text(key), new LongWritable(val));
		}
		catch(Exception e) {
			//  Block of code to handle errors
		}
		
	}

}
