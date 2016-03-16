package com.github.jliblbfgs;

import com.google.inject.Inject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.function.BiFunction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 3/15/16.
 */
@Test
@Guice(modules = TestModule.class)
public class LBFGSVectorTest {
    @Inject
    LBFGSVector.IFactory vectorFactory;

    @DataProvider(name = "twoVectors")
    public Object[][] twoVectors() {
        return new Object[][]{
                {
                        "dot",
                        vectorFactory.createVector(new double[]{1, 2, 3}),
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                        (BiFunction<LBFGSVector, LBFGSVector, Double>) LBFGSVector::dot,
                        32d
                },
                {
                        "norm",
                        vectorFactory.createVector(new double[]{1, 2, 3}),
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                        (BiFunction<LBFGSVector, LBFGSVector, Double>) LBFGSVector::norm,
                        Math.sqrt(32d)
                },
                {
                        "norminv",
                        vectorFactory.createVector(new double[]{1, 2, 3}),
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                        (BiFunction<LBFGSVector, LBFGSVector, Double>) LBFGSVector::norminv,
                        1d / Math.sqrt(32d)
                },
        };
    }

    @Test(dataProvider = "twoVectors")
    public void testTwoVectorDot(
            String desc,
            LBFGSVector vector1,
            LBFGSVector vector2,
            BiFunction<LBFGSVector, LBFGSVector, Double> op,
            double expected) throws Exception {
        assertThat(op.apply(vector1, vector2), is(expected));
    }

    @Test
    public void testConstructors() throws Exception {
        double[] doubles = {1, 2, 3};
        LBFGSVector origVector = vectorFactory.createVector(doubles);
        LBFGSVector copiedVector = vectorFactory.createVector(origVector);
        assertThat(origVector, is(copiedVector));
        assertThat(origVector, not(sameInstance(copiedVector)));
        assertThat(origVector.getValues(), not(sameInstance(copiedVector.getValues())));
    }
}