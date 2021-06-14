package topkproducts;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class mapper1 extends Mapper<LongWritable, Text, Text, LongWritable> {
	String dt;
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		try {
			String stmt = ivalue.toString();
			String[] arr= stmt.split(",");
			String dtInfor = arr[1];
			String m_y = dtInfor.substring(4,6) + "-" + dtInfor.substring(0,4);

			if( m_y.contains(dt) || dt.equals("all")) {

				String product = arr[3];
				long amount = Long.parseLong(arr[5]);
				context.write(new Text(product), new LongWritable(amount));

			}
		}
		catch(Exception e) {
  				//  Block of code to handle errors
		}
	}
	@Override
	public void setup(Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		dt = conf.get("dt");
	}
}
