package com.github.jliblbfgs;

import com.google.inject.Inject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 3/15/16.
 */
@Test
@Guice(modules = TestModule.class)
public class LBFGSVectorTest {
    @Inject
    LBFGSVector.IFactory vectorFactory;

    @DataProvider(name = "twoVectorDoubleOps")
    public Object[][] twoVectorDoubleOps() {
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

    @Test(dataProvider = "twoVectorDoubleOps")
    public void testTwoVectorDoubleOps(
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
        assertThat(origVector.hashCode(), is(copiedVector.hashCode()));
        assertThat(origVector, not(sameInstance(copiedVector)));
        assertThat(origVector.getValues(), not(sameInstance(copiedVector.getValues())));
    }

    @DataProvider(name = "vectorOperators")
    public Object[][] vectorOperators() {
        return new Object[][]{
                {
                        "mul",
                        vectorFactory.createVector(new double[]{1, 2, 3}),
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                        (BiConsumer<LBFGSVector, LBFGSVector>) LBFGSVector::mul,
                        vectorFactory.createVector(new double[]{4, 10, 18}),
                },
                {
                        "copyFrom",
                        vectorFactory.createVector(new double[]{1, 2, 3}),
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                        (BiConsumer<LBFGSVector, LBFGSVector>) LBFGSVector::copyFrom,
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                },
                {
                        "copyNegativeFrom",
                        vectorFactory.createVector(new double[]{1, 2, 3}),
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                        (BiConsumer<LBFGSVector, LBFGSVector>) LBFGSVector::copyNegativeFrom,
                        vectorFactory.createVector(new double[]{-4, -5, -6}),
                },
        };
    }

    @Test(dataProvider = "vectorOperators")
    public void testVectorOperators(
            String desc,
            LBFGSVector vector1,
            LBFGSVector vector2,
            BiConsumer<LBFGSVector, LBFGSVector> op,
            LBFGSVector expected) throws Exception {
        assertThat(vector1, not(expected));
        op.accept(vector1, vector2);
        assertThat(vector1, is(expected));
    }

    @DataProvider(name = "vectorDoubleOps")
    public Object[][] vectorDoubleOps() {
        LBFGSVector v1;
        return new Object[][]{
                {
                        "addWithMultiplier * 1",
                        v1 = vectorFactory.createVector(new double[]{1, 2, 3}),
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                        1d,
                        (BiConsumer<LBFGSVector, Double>) v1::addWithMultiplier,
                        vectorFactory.createVector(new double[]{5, 7, 9}),
                },
                {
                        "addWithMultiplier * 2",
                        v1 = vectorFactory.createVector(new double[]{1, 2, 3}),
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                        2d,
                        (BiConsumer<LBFGSVector, Double>) v1::addWithMultiplier,
                        vectorFactory.createVector(new double[]{9, 12, 15}),
                },
        };
    }

    @Test(dataProvider = "vectorDoubleOps")
    public void testVectorDoubleOps(
            String desc,
            LBFGSVector vector1,
            LBFGSVector vector2,
            double amount,
            BiConsumer<LBFGSVector, Double> op,
            LBFGSVector expected) throws Exception {
        assertThat(vector1, not(expected));
        op.accept(vector2, amount);
        assertThat(vector1, is(expected));
    }

    @DataProvider(name = "threeVectorOps")
    public Object[][] twoVectorOps() {
        LBFGSVector v1;
        return new Object[][]{
                {
                        "copyDiff",
                        v1 = vectorFactory.createVector(new double[]{1, 2, 3}),
                        vectorFactory.createVector(new double[]{4, 5, 6}),
                        vectorFactory.createVector(new double[]{3, 2, 1}),
                        (BiConsumer<LBFGSVector, LBFGSVector>) v1::copyDiff,
                        vectorFactory.createVector(new double[]{1, 3, 5}),
                },
        };
    }

    @Test(dataProvider = "threeVectorOps")
    public void testThreeVectorOps(
            String desc,
            LBFGSVector vector1,
            LBFGSVector vector2,
            LBFGSVector vector3,
            BiConsumer<LBFGSVector, LBFGSVector> op,
            LBFGSVector expected) throws Exception {
        assertThat(vector1, not(expected));
        op.accept(vector2, vector3);
        assertThat(vector1, is(expected));
    }

    @DataProvider(name = "doubleOps")
    public Object[][] doubleOps() {
        return new Object[][]{
                {
                        "fill 1",
                        vectorFactory.createVector(new double[]{1, 2, 3}),
                        1,
                        (BiConsumer<LBFGSVector, Double>) LBFGSVector::fill,
                        vectorFactory.createVector(new double[]{1, 1, 1}),
                },
                {
                        "scale 2",
                        vectorFactory.createVector(new double[]{1, 2, 3}),
                        2,
                        (BiConsumer<LBFGSVector, Double>) LBFGSVector::scale,
                        vectorFactory.createVector(new double[]{2, 4, 6}),
                },
        };
    }

    @Test(dataProvider = "doubleOps")
    public void testDoublOps(
            String desc,
            LBFGSVector vector1,
            double amount,
            BiConsumer<LBFGSVector, Double> op,
            LBFGSVector expected) throws Exception {
        assertThat(vector1, not(expected));
        op.accept(vector1, amount);
        assertThat(vector1, is(expected));
    }

    @Test
    public void testHashCode() throws Exception {
        LBFGSVector vector = vectorFactory.createVector(1);
        vector.fill(5);
        LBFGSVector vector2 = vectorFactory.createVector(1);
        vector2.getValues()[0] = 2;
        assertThat(vector, not(vector2));
        assertThat(vector.hashCode(), not(vector2.hashCode()));
        vector2.getValues()[0] = 5;
        assertThat(vector, is(vector2));
        assertThat(vector.hashCode(), is(vector2.hashCode()));
    }
}
