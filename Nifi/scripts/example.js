var flowFile = session.get();

if (flowFile !== null) {

    var StreamCallback = Java.type("org.apache.nifi.processor.io.StreamCallback");
    var IOUtils = Java.type("org.apache.commons.io.IOUtils");
    var StandardCharsets = Java.type("java.nio.charset.StandardCharsets");
	var myCsvRow = flowFile.getAttribute('filename');
    var content = myCsvRow.split('-');
    var shop = content[1];
    var dt = content[2];
    flowFile = session.write(flowFile, new StreamCallback(function(inputStream, outputStream) {
        // Read input FlowFile content
        var inputArr = IOUtils.toString(inputStream, StandardCharsets.UTF_8).split("\n");
        var outputArr = [];
        for(var index = 0; index < inputArr.length; index++){
        	if(inputArr.trim().length == 0) continue;
            outputArr.push(shop +"," + dt + "," + inputArr[index].trim());
        }
        var outputText = outputArr.join("\n");
        // Write output content
        outputStream.write(outputText.getBytes(StandardCharsets.UTF_8));
    }));

    // Finish by transferring the FlowFile to an output relationship
    session.transfer(flowFile, REL_SUCCESS);
}
