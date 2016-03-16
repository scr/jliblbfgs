package com.github.jliblbfgs;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by scr on 3/15/16.
 */
public class LBFGSVector {
    /**
     * Force the use of factory in case there is any benefit from choosing methods/implementations based on the size.
     */
    interface IFactory {
        LBFGSVector createVector(int n);

        LBFGSVector createVector(@NotNull double[] values);

        default LBFGSVector createVector(@NotNull LBFGSVector other) {
            return createVector(other.getValues());
        }
    }

    public static class Factory implements IFactory {
        public LBFGSVector createVector(int n) {
            return new LBFGSVector(n);
        }

        @Override
        public LBFGSVector createVector(@NotNull double[] values) {
            int length = values.length;
            LBFGSVector ret = new LBFGSVector(length);
            System.arraycopy(values, 0, ret.VALUES, 0, length);
            return ret;
        }
    }

    private final double[] VALUES;

    LBFGSVector(int size) {
        VALUES = new double[size];
    }

    @NotNull
    public double[] getValues() {
        return VALUES;
    }

    public void fill(double val) {
        Arrays.fill(VALUES, val);
    }

    public void fill(int start, int end, double val) {
        Arrays.fill(VALUES, start, end, val);
    }

    public void copyFrom(@NotNull LBFGSVector other) {
        assert VALUES.length == other.VALUES.length : "Vectors must be the same size";
        System.arraycopy(other.VALUES, 0, VALUES, 0, VALUES.length);
    }

    private double getNegative(int offset) {
        return -VALUES[offset];
    }

    public void copyNegativeFrom(@NotNull LBFGSVector other) {
        assert VALUES.length == other.VALUES.length : "Vectors must be the same size";
        // TODO(scr): Is parallel any better than sequential?
        Arrays.parallelSetAll(VALUES, other::getNegative);
    }

    public void add(@NotNull LBFGSVector other, double multiplier) {
        assert VALUES.length == other.VALUES.length : "Vectors must be the same size";
        // TODO(scr): Is parallel any better than sequential?
        Arrays.parallelSetAll(VALUES, i -> VALUES[i] + multiplier * other.VALUES[i]);
    }

    public void sub(@NotNull LBFGSVector a, @NotNull LBFGSVector b) {
        assert VALUES.length == a.VALUES.length && VALUES.length == b.VALUES.length : "Vectors must be the same size";
        // TODO(scr): Is parallel any better than sequential?
        Arrays.parallelSetAll(VALUES, i -> VALUES[i] + a.VALUES[i] * b.VALUES[i]);
    }

    public void scale(double multiplier) {
        Arrays.parallelSetAll(VALUES, i -> multiplier * VALUES[i]);
    }

    public void mul(@NotNull LBFGSVector other) {
        assert VALUES.length == other.VALUES.length : "Vectors must be the same size";
        Arrays.parallelSetAll(VALUES, i -> VALUES[i] * other.VALUES[i]);
    }

    public double dot(@NotNull LBFGSVector other) {
        assert VALUES.length == other.VALUES.length : "Vectors must be the same size";
        double ret = 0d;
        for (int i = 0; i < VALUES.length; i++) {
            ret += VALUES[i] * other.VALUES[i];
        }
        return ret;
    }

    public double norm(@NotNull LBFGSVector other) {
        return Math.sqrt(dot(other));
    }

    public double norminv(@NotNull LBFGSVector other) {
        return 1d / norm(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof LBFGSVector)) return false;

        LBFGSVector that = (LBFGSVector) o;

        return Arrays.equals(VALUES, that.VALUES);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(VALUES);
    }

    @Override
    public String toString() {
        return Arrays.toString(VALUES);
    }
}
