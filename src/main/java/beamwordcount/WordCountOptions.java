package beamwordcount;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface WordCountOptions extends PipelineOptions {
    @Description("Input File to process.")
    @Default.String("pom.xml")
    String getInputFile();
    void setInputFile(String inputFile);
    
    @Description("Output file prefix")
    @Default.String("wordcount-output")
    String getOutputFile();
    void setOutputFile(String outputFile);
  }
