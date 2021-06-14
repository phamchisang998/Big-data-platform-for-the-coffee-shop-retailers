package topkproducts;

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

public class reducer2 extends Reducer<Text, LongWritable, Text, LongWritable>{
	private Map<String,Long> listItems=new HashMap<String,Long>();
	public void reduce(Text _key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
		// process values
		long sum=0;
		if(listItems.containsKey(_key.toString())) {
			sum=listItems.get(_key.toString());
		}
		for (LongWritable val : values) {
			sum+=val.get();
		}
		//result.set(sum);
		//context.write(_key, result);
		listItems.put(_key.toString(), sum);
	}
	@Override
	public void cleanup(Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		String strTopK = conf.get("topK");
		int topK=strTopK.equals("all")?-1:Integer.parseInt(strTopK);

		Map<String,Long> sortedItems=new LinkedHashMap<String, Long>();
		listItems.entrySet().stream()
		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		.forEachOrdered(x->sortedItems.put(x.getKey(), x.getValue()));
		int count=0;
		for(String key:sortedItems.keySet()) {
			if(topK == -1 || count<topK) {
				context.write(new Text(key), new LongWritable(sortedItems.get(key)));
			}else {
				break;
			}
			count++;
		}
	}
}
