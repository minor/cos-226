Programming Assignment 7: Fraud Detection

/* *****************************************************************************
 *  Describe how you implemented the Clustering constructor
 **************************************************************************** */

For the clustering constructor, I split it into different parts: validation and
processing. After validating inputs, it creates a weighted graph using Euclidean
distances and then creates an MST based ofo that graph. Then, it builds a
cluster graph by using the minimum weight edges from the MST. Then, it uses
Connected Components to assign a point to its corresponding cluster. The reason
I split it into these parts was because the logic of the class didn't seem too
drastic, and I was able to implement it efficiently.


/* *****************************************************************************
 *  Describe how you implemented the WeakLearner constructor
 **************************************************************************** */

The constructor starts off by validating the input data since there are many
validations to be done. Then, it initializes the number of features and starts
to train. The train() method iterates through each feature, sorts data by
feature values, and calculates the best threshold for classification based
on weighted accuracies. After this, I implemented the predict() method, which
utilizes these trained predictors to classify new samples. Aferwards, I
implemented the main method.

/* *****************************************************************************
 *  Consider the large_training.txt and large_test.txt datasets.
 *  Run the boosting algorithm with different values of k and T (iterations),
 *  and calculate the test data set accuracy and plot them below.
 *
 *  (Note: if you implemented the constructor of WeakLearner in O(kn^2) time
 *  you should use the training.txt and test.txt datasets instead, otherwise
 *  this will take too long)
 **************************************************************************** */

      k          T         test accuracy       time (seconds)
   --------------------------------------------------------------------------
      1         100             0.506               0.372
      2         100             0.682               0.47
      4         100             0.816               0.751
      8         100             0.93                1.001
      16        100             0.972               1.389
      32        100             0.968 (overfit)     1.848
      8         100             0.93                1.001 [repeat to show trend]
      8         200             0.929               1.535
      8         400             0.929               2.496
      8         800             0.937               4.432
      8         1600            0.93                8.406

/* *****************************************************************************
 *  Find the values of k and T that maximize the test data set accuracy,
 *  while running under 10 second. Write them down (as well as the accuracy)
 *  and explain:
 *   1. Your strategy to find the optimal k, T.
 *   2. Why a small value of T leads to low test accuracy.
 *   3. Why a k that is too small or too big leads to low test accuracy.
 **************************************************************************** */
Optimal value for k and T: k = 16, T = 100 with test accuracy = 0.972

1. My strategy was trial and error, since I was trying to see if there was some
mathematical pattern that I could deduce for k and T and their relationship to
the test accuracy.

2. A small value of T leads to low test accuracy because the algorithm won't
 have enough iterations to learn patterns in the data. Boosting literally
 relies on the fact that there are multiple iterations to learn from, and
 so a small T might not allow this.

 3. A small k may lead to the oversimplification of the data which would make
 it really hard for the boosting algorithm to accurately distinguish between
 classes accurately, which would ultimately decrease test accuracy. But, if k
 is too large, then it may lead to overfitting, which is what we saw above.
 This means that the model learns the noise within the data, which isn't ideal.

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

n/a

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

n/a

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

n/a
