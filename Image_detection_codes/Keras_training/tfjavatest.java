import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

/** One time initialization: */
TensorFlowInferenceInterface tensorflow = new TensorFlowInferenceInterface(getAssets(), "file:///android_asset/model.pb");

/** Continuous inference (floats used in example, can be any primitive): */

// loading new input
tensorflow.feed("input:0", input, INPUT_SHAPE); // INPUT_SHAPE is an long[] of expected shape, input is a float[] with the input data

// running inference for given input and reading output
String outputNode = "output:0";
String[] outputNodes = {outputNode};
boolean enableStats = false;
tensorflow.run(outputNodes, enableStats);
tensorflow.fetch(outputNode, output); // output is a preallocated float[] in the size of the expected output vector