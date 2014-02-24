package ch.yax.hive.udf.util.text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import ch.yax.hive.udf.util.data.ContentHelper;

/*
 * based on the example from http://stackoverflow.com/questions/9707825/basic-text-classification-with-weka-in-java
 */
public class SimpleTextClassifier {

	private Instances trainingData;
	private StringToWordVector filter;
	private Classifier classifier;
	private FastVector classValues;
	private FastVector attributes;

	private Instances filteredData;
	private ContentHelper contentHelper;

	public SimpleTextClassifier(ContentHelper contentHelper,
			Classifier classifier) {
		this(contentHelper, classifier, 10);
	}

	public SimpleTextClassifier(ContentHelper contentHelper,
			Classifier classifier, int startSize) {
		this.filter = new StringToWordVector();
		this.classifier = classifier;
		// Create vector of attributes.
		this.attributes = new FastVector(2);
		// Add attribute for holding texts.
		this.attributes.addElement(new Attribute("text", (FastVector) null));
		// Add class attribute.
		this.classValues = new FastVector(startSize);
		this.contentHelper = contentHelper;

		initClassifier();

	}

	private void initClassifier() {
		// init catgeories
		Set<String> categories = this.contentHelper.getCategories(0);
		for (String category : categories) {
			addCategory(category);

		}
		// addCategory("UNKNOW");
		setupAfterCategorysAdded();

		// init data
		List<List<String>> trainigData = contentHelper.getTokens();
		for (List<String> data : trainigData) {
			String classValue = data.get(0);
			String message = data.get(1);
			addData(message, classValue);
		}
	}

	private void addCategory(String category) {
		category = category.toLowerCase();

		// if required, double the capacity.
		int capacity = classValues.capacity();
		if (classValues.size() > (capacity - 5)) {
			classValues.setCapacity(capacity * 2);
		}
		classValues.addElement(category);
	}

	private void addData(String message, String classValue) {

		message = message.toLowerCase();
		classValue = classValue.toLowerCase();
		// Make message into instance.
		Instance instance = makeInstance(message, trainingData);
		// Set class value for instance.
		instance.setClassValue(classValue);
		// Add instance to training data.
		trainingData.add(instance);

	}

	private void buildClassifier() throws Exception {

		// Initialize filter and tell it about the input format.
		filter.setInputFormat(trainingData);
		// Generate word counts from the training data.
		filteredData = Filter.useFilter(trainingData, filter);
		// Rebuild classifier.
		classifier.buildClassifier(filteredData);

	}

	public Map<String, Double> classifyMessage(String message) throws Exception {
		message = message.toLowerCase();
		// Check whether classifier has been built.
		if (trainingData.numInstances() == 0) {
			throw new Exception("No classifier available.");
		}
		buildClassifier();
		Instances testset = trainingData.stringFreeStructure();
		Instance testInstance = makeInstance(message, testset);

		// Filter instance.
		filter.input(testInstance);
		Instance filteredInstance = filter.output();

		Map<String, Double> result = new HashMap<String, Double>();

		double[] distributions = classifier
				.distributionForInstance(filteredInstance);

		for (int index = 0; index < distributions.length; index++) {
			result.put(filteredInstance.classAttribute().value(index),
					distributions[index]);
		}

		return result;

	}

	private Instance makeInstance(String text, Instances data) {
		// Create instance of length two.
		Instance instance = new Instance(2);
		// Set value for message attribute
		Attribute messageAtt = data.attribute("text");
		instance.setValue(messageAtt, messageAtt.addStringValue(text));
		// Give instance access to attribute information from the dataset.
		instance.setDataset(data);
		return instance;
	}

	private void setupAfterCategorysAdded() {
		attributes.addElement(new Attribute("class", classValues));
		// Create dataset with initial capacity of 100, and set index of class.
		trainingData = new Instances("MessageClassificationProblem",
				attributes, 100);
		trainingData.setClassIndex(trainingData.numAttributes() - 1);

	}

}
