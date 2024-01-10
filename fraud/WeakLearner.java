import java.util.Arrays;
import java.util.Comparator;

public class WeakLearner {
    private int dimensionPredictor; // dimension to separate data
    private double valuePredictor; // value used to separate data
    private int signPredictor; // sign used to separate data
    private int nFeatures; // number of features in the learner

    // create weak learner and train
    public WeakLearner(double[][] input, double[] weights, int[] labels) {
        // handle all the validations
        if (input == null || weights == null || labels == null)
            throw new IllegalArgumentException("Invalid input: null argument.");
        if (input.length == 0 || weights.length == 0 || labels.length == 0)
            throw new IllegalArgumentException("Invalid input: empty array.");
        if (input.length != weights.length || input.length != labels.length)
            throw new IllegalArgumentException("Array lengths are not equal");
        for (double[] row : input) {
            if (row == null)
                throw new IllegalArgumentException("Invalid input: sub-array"
                                                           + "null");
            if (row.length != input[0].length)
                throw new IllegalArgumentException("Invalid input: sub-arrays"
                                                           + "lengths diff");
        }
        for (double weight : weights) {
            if (weight < 0)
                throw new IllegalArgumentException("Invalid input: negative"
                                                           + "weight");
        }
        for (int label : labels) {
            if (label != 0 && label != 1)
                throw new IllegalArgumentException("Invalid input: labels"
                                                           + "not binary");
        }

        this.nFeatures = input[0].length;
        train(input, weights, labels);
    }

    // train
    private void train(double[][] input, double[] weights, int[] labels) {
        int n = input.length;
        double maxWeightedAccuracy = -1;

        for (int dimension = 0; dimension < nFeatures; dimension++) {
            double[][] thresholdData = new double[n][3];

            for (int i = 0; i < n; i++) {
                thresholdData[i][0] = input[i][dimension]; // threshold
                thresholdData[i][1] = weights[i]; // weight
                thresholdData[i][2] = labels[i]; // label
            }

            // lambda func to take a and return a[0]
            Arrays.sort(thresholdData, Comparator.comparingDouble(a -> a[0]));

            double weightedTruePositives = 0;
            double weightedFalsePositives = 0;

            for (double[] acc : thresholdData) {
                if (acc[2] == 1) {
                    weightedTruePositives += acc[1];
                }
                else {
                    weightedFalsePositives += acc[1];
                }
            }

            double bestThreshold = Double.NEGATIVE_INFINITY;
            double bestAccuracy = 0;
            int bestSign = 1;
            double truePositives = 0;
            double falsePositives = 0;

            for (int i = 0; i < thresholdData.length; i++) {
                double[] acc = thresholdData[i];

                if (acc[2] == 1) {
                    truePositives += acc[1];
                }
                else {
                    falsePositives += acc[1];
                }

                double accuracy = Math.max(truePositives, falsePositives);

                if (accuracy > bestAccuracy) {
                    bestAccuracy = accuracy;
                    bestThreshold = acc[0];
                    if (truePositives > falsePositives) {
                        bestSign = 1;
                    }
                    else {
                        bestSign = 0;
                    }
                }

                if (i < thresholdData.length - 1 && acc[0] !=
                        thresholdData[i + 1][0]) {
                    double nextThreshold = (acc[0] +
                            thresholdData[i + 1][0]) / 2;
                    double max = Math.max(weightedTruePositives - truePositives
                                                  + falsePositives,
                                          truePositives +
                                                  weightedFalsePositives -
                                                  falsePositives);

                    if (max > bestAccuracy) {
                        bestAccuracy = max;
                        bestThreshold = nextThreshold;

                        if (weightedTruePositives - truePositives +
                                falsePositives > truePositives +
                                weightedFalsePositives - falsePositives) {
                            bestSign = 0;
                        }
                        else {
                            bestSign = 1;
                        }
                    }
                }
            }

            if (bestAccuracy > maxWeightedAccuracy) {
                maxWeightedAccuracy = bestAccuracy;
                this.dimensionPredictor = dimension;
                this.valuePredictor = bestThreshold;
                this.signPredictor = bestSign;
            }
        }
    }

    // return the prediction of the learner for a new sample
    public int predict(double[] sample) {
        if (sample == null || sample.length != nFeatures) {
            throw new IllegalArgumentException("Invalid input: array null or"
                                                       + "length incorrect.");
        }
        int prediction; // init prediction
        if (sample[dimensionPredictor] <= valuePredictor) {
            prediction = 1;
        }
        else {
            prediction = 0;
        }
        if (signPredictor == 1) {
            return prediction;
        }
        else {
            return 1 - prediction;
        }
    }

    // return the dimension the learner uses to separate the data
    public int dimensionPredictor() {
        return dimensionPredictor;
    }

    // return the value the learner uses to separate the data
    public double valuePredictor() {
        return valuePredictor;
    }

    // return the sign the learner uses to separate the data
    public int signPredictor() {
        return signPredictor;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // test data
        double[][] input = {
                { 1.0, 2.0 },
                { 1.5, 2.5 },
                { 2.0, 3.0 }
        };
        double[] weights = { 1.0, 1.0, 1.0 };
        int[] labels = { 1, 0, 1 };

        // initialize WeakLearner
        WeakLearner learner = new WeakLearner(input, weights, labels);

        // test dimensionPredictor, valuePredictor, signPredictor
        int dimension = learner.dimensionPredictor();
        double value = learner.valuePredictor();
        int sign = learner.signPredictor();

        if (dimension < 0 || dimension >= input[0].length) {
            System.out.println("Dimension predictor out of range.");
        }
        if (value < 0) {
            System.out.println("Value predictor is negative.");
        }
        if (sign != 0 && sign != 1) {
            System.out.println("Sign predictor is not binary.");
        }

        // test predict method
        for (double[] sample : input) {
            int prediction = learner.predict(sample);
            if (prediction != 0 && prediction != 1) {
                System.out.println("Prediction is not binary.");
            }
        }

        // test with different data to ensure it's dynamic
        double[][] newInput = {
                { 2.0, 1.0 },
                { 2.5, 1.5 },
                { 3.0, 2.0 }
        };
        int[] newLabels = { 0, 1, 0 };

        WeakLearner newLearner = new WeakLearner(newInput, weights, newLabels);
        if (newLearner.dimensionPredictor() == dimension &&
                newLearner.valuePredictor() == value) {
            System.out.println("Same predictors on different data.");
        }
    }
}
