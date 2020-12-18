package org.moara.classification;


import org.junit.Assert;
import org.junit.Test;
import org.moara.classification.binary.BinaryClassificationEvaluation;
import org.moara.classification.multinomial.MultinomialClassificationEvaluation;

public class ClassificationTest {


    int[][] multiClassData = {
            {9, 1, 0, 0},
            {1, 15, 3, 1},
            {5, 0, 24, 1},
            {0, 4, 1, 15}
    };

    int[][] binaryData = {
            {9, 1},
            {1, 15}
    };

    @Test
    public void testBinaryClassification() {
        BinaryClassificationEvaluation evaluation1 = new BinaryClassificationEvaluation(binaryData);

        BinaryClassificationEvaluation evaluation2 = new BinaryClassificationEvaluation(binaryData[0][0], binaryData[0][1], binaryData[1][0], binaryData[1][1]);


        Assert.assertEquals(evaluation1.getAccuracy(), evaluation2.getAccuracy(), 0.001);
        Assert.assertEquals(evaluation1.getErrorRate(), evaluation2.getErrorRate(), 0.001);
        Assert.assertEquals(evaluation1.getPrecision(), evaluation2.getPrecision(), 0.001);
        Assert.assertEquals(evaluation1.getSensitivity(), evaluation2.getSensitivity(), 0.001);
        Assert.assertEquals(evaluation1.getSpecificity(), evaluation2.getSpecificity(), 0.001);
        Assert.assertEquals(evaluation1.getF1Score(), evaluation2.getF1Score(), 0.001);
        Assert.assertEquals(evaluation1.getGeometricMean(), evaluation2.getGeometricMean(), 0.001);

    }

    @Test
    public void testMultinomialClassificationEvaluation() {
        BinaryClassificationEvaluation evaluation1 = new BinaryClassificationEvaluation(binaryData);

        MultinomialClassificationEvaluation evaluation2 = new MultinomialClassificationEvaluation(binaryData);

        System.out.println(evaluation1.getPrecision());
        System.out.println(evaluation2.getPrecisions()[1]);


        Assert.assertEquals(evaluation1.getAccuracy(), evaluation2.getAccuracy(), 0.001);
        Assert.assertEquals(evaluation1.getErrorRate(), evaluation2.getErrorRate(), 0.001);

        Assert.assertEquals(evaluation1.getPrecision(), evaluation2.getPrecision(), 0.001);
        Assert.assertEquals(evaluation1.getSensitivity(), evaluation2.getSensitivity(), 0.001);
        Assert.assertEquals(evaluation1.getSpecificity(), evaluation2.getSpecificity(), 0.001);

        Assert.assertEquals(evaluation1.getF1Score(), evaluation2.getF1Score(), 0.001);
        Assert.assertEquals(evaluation1.getGeometricMean(), evaluation2.getGeometricMean(), 0.001);


    }
}
