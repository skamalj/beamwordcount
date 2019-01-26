package beamwordcount;

import java.util.Arrays;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.FlatMapElements;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.TypeDescriptors;
public class WordCount {

  public static void main(String[] args) {

	PipelineOptionsFactory.register(WordCountOptions.class);  
	WordCountOptions options = PipelineOptionsFactory
    		.fromArgs(args)
    		.withValidation()
    		.as(WordCountOptions.class);

        Pipeline p = Pipeline.create(options);
        p.apply(TextIO.read().from(options.getInputFile()))
        .apply(
            FlatMapElements.into(TypeDescriptors.strings())
                .via((String word) -> Arrays.asList(word.split("[^\\p{L}]+"))))

        .apply(Filter.by((String word) -> !word.isEmpty()))

        .apply(Count.perElement())
        
        .apply(
            MapElements.into(TypeDescriptors.strings())
            .via(
                    (KV<String, Long> wordCount) ->
                        wordCount.getKey() + ": " + wordCount.getValue()))
        .apply(TextIO.write().to(options.getOutputFile()));

    System.out.println(options.getRunner().toString());
    if (options.getRunner().toString() == "org.apache.beam.runners.direct.DirectRunner") 
    	p.run().waitUntilFinish();
    else 
    	p.run();
  }
}