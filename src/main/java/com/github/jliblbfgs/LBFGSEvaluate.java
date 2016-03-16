package com.github.jliblbfgs;

/**
 * Created by scr on 3/14/16.
 */
interface LBFGSEvaluate {
    /**
     * Callback interface to provide objective function and gradient evaluations.
     * <p>
     * The lbfgs() function call this function to obtain the values of objective
     * function and its gradients when needed. A client program must implement
     * this function to evaluate the values of the objective function and its
     * gradients, given current values of variables.
     *
     * @param x    The current values of variables.
     * @param g    The gradient vector. The callback function must compute
     *             the gradient values for the current variables.
     * @param n    The number of variables.
     * @param step The current step of the line search routine.
     * @return The value of the objective function for the current
     * variables.
     */
    double lbfgsEvaluate(
            double[] x,
            double[] g,
            int n,
            double step);
}
