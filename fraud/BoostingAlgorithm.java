import edu.princeton.cs.algs4.Point2D;

import java.util.ArrayList;
import java.util.Arrays;

public class BoostingAlgorithm {

    private ArrayList<WeakLearner> allLearners; // list of weak learners
    private Clustering clustering; // clustering
    private double[] weights; // weights for each point
    private int[] labels; // labels for each point
    private int k; // int to hold number of clusters
    private double[][] dReducedInput; // dimensionally reduced input

    // create the clusters and initialize your data structures
    public BoostingAlgorithm(double[][] input, int[] labels,
                             Point2D[] locations, int k) {
        // handle all the validations
        validateInput(input, labels, locations, k);

        // create a copy and store into labels
        this.labels = Arrays.copyOf(labels, labels.length);

        // init instance variables and weights
        allLearners = new ArrayList<>();
        clustering = new Clustering(locations, k);
        weights = new double[input.length];
        this.k = k;
        for (int i = 0; i < input.length; i++) {
            weights[i] = 1.0 / input.length;
        }

        // dimensionality reduction
        this.dReducedInput = new double[input.length][];
        for (int i = 0; i < input.length; i++) {
            dReducedInput[i] = clustering.reduceDimensions(Arrays.copyOf(
                    input[i], input[i].length));
        }
    }

    // helper method to validate the inputs
    private void validateInput(double[][] input, int[] labelsM,
                               Point2D[] locations, int kM) {
        if (input == null || labelsM == null || locations == null ||
                input.length != labelsM.length
                || kM < 1 || kM > locations.length) {
            throw new IllegalArgumentException("Invalid input.");
        }

        for (double[] row : input) {
            if (row == null || row.length != locations.length) {
                throw new IllegalArgumentException("Invalid input:"
                                                           + "array element.");
            }
        }
    }

    // return the current weights
    public double[] weights() {
        return Arrays.copyOf(weights, weights.length); // grab a copy
    }

    // apply one step of the boosting algorithm
    public void iterate() {
        WeakLearner learner = new WeakLearner(dReducedInput, weights, labels);
        allLearners.add(learner);
        for (int i = 0; i < weights.length; i++) {
            int prediction = learner.predict(dReducedInput[i]);
            if (prediction != labels[i]) {
                weights[i] = weights[i] * 2;
            }
        }

        // weights normalization
        double sumWeights = 0;
        for (int i = 0; i < weights.length; i++) {
            sumWeights = sumWeights + weights[i];
        }
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] / sumWeights;
        }
    }

    // return the prediction of the learner for a new sample
    public int predict(double[] sample) {
        if (sample == null) {
            throw new IllegalArgumentException("Invalid input: null sample.");
        }
        double[] reducedSample = clustering.reduceDimensions(sample);
        if (reducedSample.length != k) {
            throw new IllegalArgumentException("Invalid input: size mismatch.");
        }

        int vote = 0;
        for (int i = 0; i < allLearners.size(); i++) {
            WeakLearner learner = allLearners.get(i);
            vote = vote + learner.predict(reducedSample);
        }

        int temp = allLearners.size() / 2;
        if (vote > temp) {
            return 1;
        }
        else {
            return 0;
        }
    }

    // main method: testing
    public static void main(String[] args) {
        // test data
        double[][] input = { { 1.0, 2.0 }, { 3.0, 4.0 } };
        int[] labels = { 0, 1 };
        Point2D[] locations = { new Point2D(1.0, 1.0), new Point2D(2.0, 2.0) };
        int k = 2;

        // test boosting algo init
        BoostingAlgorithm ba = new BoostingAlgorithm(input, labels,
                                                     locations, k);
        System.out.println("Initialization test passed.");

        // test weights()
        double[] initialWeights = ba.weights();
        if (initialWeights.length == input.length && initialWeights[0] == 0.5) {
            System.out.println("Weights initialization test passed.");
        }
        else {
            System.out.println("Weights initialization test failed.");
        }

        // test iterate()
        ba.iterate();
        double[] updatedWeights = ba.weights();
        if (updatedWeights[0] != initialWeights[0]) {
            System.out.println("Iterate method test passed.");
        }
        else {
            System.out.println("Iterate method test failed.");
        }

        // test predict()
        double[] sample = { 2.0, 3.0 };
        int prediction = ba.predict(sample);
        System.out.println("Prediction: " + prediction);
    }
}
