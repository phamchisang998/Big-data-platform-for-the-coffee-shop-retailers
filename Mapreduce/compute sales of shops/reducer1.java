package salesofshops;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class reducer1 extends Reducer<Text, LongWritable, Text, LongWritable>{
	public void reduce(Text _key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
		long sum=0;
		for (LongWritable val : values) {
			sum+=val.get();
		}
		context.write(_key, new LongWritable(sum));
	}
}
