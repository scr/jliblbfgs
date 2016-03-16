package com.github.jliblbfgs;

import org.jetbrains.annotations.NotNull;

/**
 * Created by scr on 3/14/16.
 */
public class LBFGSParams {
    /**
     * The number of corrections to approximate the inverse hessian matrix.
     * The L-BFGS routine stores the computation results of previous m
     * iterations to approximate the inverse hessian matrix of the current
     * iteration. This parameter controls the size of the limited memories
     * (corrections). The default value is 6. Values less than 3 are
     * not recommended. Large values will result in excessive computing time.
     */
    public final int m;

    /**
     * Epsilon for convergence test.
     * {@literal
     * This parameter determines the accuracy with which the solution is to
     * be found. A minimization terminates when
     * ||g|| < \ref epsilon * max(1, ||x||),
     * where ||.|| denotes the Euclidean (L2) norm. The default value is
     * 1e-5.}
     */
    public final double epsilon;

    /**
     * Distance for delta-based convergence test.
     * This parameter determines the distance, in iterations, to compute
     * the rate of decrease of the objective function. If the value of this
     * parameter is zero, the library does not perform the delta-based
     * convergence test. The default value is 0.
     */
    public final int past;

    /**
     * Delta for convergence test.
     * {@literal
     * This parameter determines the minimum rate of decrease of the
     * objective function. The library stops iterations when the
     * following condition is met:
     * (f' - f) / f < \ref delta,
     * where f' is the objective value of \ref past iterations ago, and f is
     * the objective value of the current iteration.
     * The default value is  0.
     * }
     */
    public final double delta;

    /**
     * The maximum number of iterations.
     * The lbfgs() function terminates an optimization process with
     * ::LBFGSERR_MAXIMUMITERATION status code when the iteration count
     * exceedes this parameter. Setting this parameter to zero continues an
     * optimization process until a convergence or error. The default value
     * is 0.
     */
    public final int max_iterations;

    /**
     * The line search algorithm.
     * This parameter specifies a line search algorithm to be used by the
     * L-BFGS routine.
     */
    public final int linesearch;

    /**
     * The maximum number of trials for the line search.
     * This parameter controls the number of function and gradients evaluations
     * per iteration for the line search routine. The default value is 20.
     */
    public final int max_linesearch;

    /**
     * The minimum step of the line search routine.
     * The default value is 1e-20. This value need not be modified unless
     * the exponents are too large for the machine being used, or unless the
     * problem is extremely badly scaled (in which case the exponents should
     * be increased).
     */
    public final double min_step;

    /**
     * The maximum step of the line search.
     * The default value is 1e+20. This value need not be modified unless
     * the exponents are too large for the machine being used, or unless the
     * problem is extremely badly scaled (in which case the exponents should
     * be increased).
     */
    public final double max_step;

    /**
     * A parameter to control the accuracy of the line search routine.
     * The default value is 1e-4. This parameter should be greater
     * than zero and smaller than 0.5.
     */
    public final double ftol;

    /**
     * A coefficient for the Wolfe condition.
     * This parameter is valid only when the backtracking line-search
     * algorithm is used with the Wolfe condition,
     * ::LBFGS_LINESEARCH_BACKTRACKING_STRONG_WOLFE or
     * ::LBFGS_LINESEARCH_BACKTRACKING_WOLFE .
     * The default value is \c 0.9. This parameter should be greater
     * the \ref ftol parameter and smaller than 1.0.
     */
    public final double wolfe;

    /**
     * A parameter to control the accuracy of the line search routine.
     * The default value is 0.9. If the function and gradient
     * evaluations are inexpensive with respect to the cost of the
     * iteration (which is sometimes the case when solving very large
     * problems) it may be advantageous to set this parameter to a small
     * value. A typical small value is 0.1. This parameter shuold be
     * greater than the ftol parameter (1e-4) and smaller than
     * 1.0.
     */
    public final double gtol;

    /**
     * The machine precision for floating-point values.
     * This parameter must be a positive value set by a client program to
     * estimate the machine precision. The line search routine will terminate
     * with the status code (::LBFGSERR_ROUNDING_ERROR) if the relative width
     * of the interval of uncertainty is less than this parameter.
     */
    public final double xtol;

    /**
     * Coeefficient for the L1 norm of variables.
     * {@literal
     * This parameter should be set to zero for standard minimization
     * problems. Setting this parameter to a positive value activates
     * Orthant-Wise Limited-memory Quasi-Newton (OWL-QN) method, which
     * minimizes the objective function F(x) combined with the L1 norm |x|
     * of the variables, {F(x) + C |x|}. This parameter is the coeefficient
     * for the |x|, i.e., C. As the L1 norm |x| is not differentiable at
     * zero, the library modifies function and gradient evaluations from
     * a client program suitably; a client program thus have only to return
     * the function value F(x) and gradients G(x) as usual. The default value
     * is zero.
     * }
     */
    public final double orthantwise_c;

    /**
     * Start index for computing L1 norm of the variables.
     * {@literal
     * This parameter is valid only for OWL-QN method
     * (i.e., orthantwise_c != 0). This parameter b (0 <= b < N)
     * specifies the index number from which the library computes the
     * L1 norm of the variables x,
     * |x| := |x_{b}| + |x_{b+1}| + ... + |x_{N}| .
     * In other words, variables x_1, ..., x_{b-1} are not used for
     * computing the L1 norm. Setting b (0 < b < N), one can protect
     * variables, x_1, ..., x_{b-1} (e.g., a bias term of logistic
     * regression) from being regularized. The default value is zero.
     * }
     */
    public final int orthantwise_start;

    /**
     * End index for computing L1 norm of the variables.
     * {@literal
     * This parameter is valid only for OWL-QN method
     * (i.e., \ref orthantwise_c != 0). This parameter e (0 < e <= N)
     * specifies the index number at which the library stops computing the
     * L1 norm of the variables x.
     * }
     */
    public final int orthantwise_end;

    LBFGSParams(@NotNull Builder builder) {

    }

    public static class Builder {
        int m;
        double epsilon;
        int past;
        double delta;
        int max_iterations;
        int linesearch;
        int max_linesearch;
        double min_step;
        double max_step;
        double ftol;
        double wolfe;
        double gtol;
        double xtol;
        double orthantwise_c;
        int orthantwise_start;
        int orthantwise_end;

        Builder m(int m) {
            this.m = m;
            return this;
        }

        Builder epsilon(double epsilon) {
            this.epsilon = epsilon;
            return this;
        }

        public LBFGSParams build() {
            return new LBFGSParams(this);
        }
    }

    public static Builder builder();
}
