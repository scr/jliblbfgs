package com.github.jliblbfgs;

/**
 * Created by scr on 3/14/16.
 */
interface LBFGSProgress {
    /**
     * Callback interface to receive the progress of the optimization process.
     * <p/>
     * The lbfgs() function call this function for each iteration. Implementing
     * this function, a client program can store or display the current progress
     * of the optimization process.
     *
     * @param x     The current values of variables.
     * @param g     The current gradient values of variables.
     * @param fx    The current value of the objective function.
     * @param xnorm The Euclidean norm of the variables.
     * @param gnorm The Euclidean norm of the gradients.
     * @param step  The line-search step used for this iteration.
     * @param n     The number of variables.
     * @param k     The iteration count.
     * @param ls    The number of evaluations called for this iteration.
     * @return Zero to continue the optimization process. non-zero value will cancel the optimization process.
     */
    int lbfgsProgress(
            double[] x,
            double[] g,
            double fx,
            double xnorm,
            double gnorm,
            double step,
            int n,
            int k,
            int ls);
}
