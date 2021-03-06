package simumatch.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An operator used to calculate bonuses in <tt>Effect</tt>s.
 */
public enum Operator {
    /** Arithmetic addition */
    ADDITION("+") {

        @Override
        public double apply (double a, double b) {
            return a + b;
        }

        @Override
        public double scale (double bonus, int times) {
            if (bonus < 0) {
                throw new IllegalArgumentException("bonus < 0 (" + bonus + ")");
            }
            if (times < 0) {
                throw new IllegalArgumentException("times < 0 (" + times + ")");
            }

            return bonus * times;
        }

    },

    /** Arithmetic subtraction */
    SUBTRACTION("-") {

        @Override
        public double apply (double a, double b) {
            return a - b;
        }

        @Override
        public double scale (double bonus, int times) {
            if (bonus < 0) {
                throw new IllegalArgumentException("bonus < 0 (" + bonus + ")");
            }
            if (times < 0) {
                throw new IllegalArgumentException("times < 0 (" + times + ")");
            }

            return bonus * times;
        }
    },

    /** Arithmetic product */
    PRODUCT("*") {

        @Override
        public double apply (double a, double b) {
            return a * b;
        }

        @Override
        public double scale (double bonus, int times) {
            if (bonus < 0) {
                throw new IllegalArgumentException("bonus < 0 (" + bonus + ")");
            }
            if (times < 0) {
                throw new IllegalArgumentException("times < 0 (" + times + ")");
            }

            return Math.pow(bonus, times);
        }
    };

    private static final Map<String, Operator> OPERATORS;
    static {
        Map<String, Operator> ops = new HashMap<String, Operator>();
        for (Operator op : Operator.values()) {
            ops.put(op.string, op);
        }

        OPERATORS = Collections.unmodifiableMap(ops);
    }

    /** String representation of the operator */
    private final String string;

    /**
     * @param string
     *            String representation of the operator
     */
    private Operator (String string) {
        this.string = string;
    }

    /**
     * Applies this operator to the given operands.
     * 
     * @param a
     *            First operand
     * @param b
     *            Second operand
     * @return Result of the operation
     */
    public abstract double apply (double a, double b);

    /**
     * Applies this operator <tt>times</tt> times over <tt>bonus</tt>.
     * 
     * @param bonus
     *            Value to be scaled
     * @param times
     *            Number of times the operator is applied
     * @return The scaled value
     * @throws IllegalArgumentException
     *             if any of the arguments is negative
     */
    public abstract double scale (double bonus, int times);

    /**
     * Applies this operator to the given operands.
     * <p>
     * This version of this method takes an <tt>int</tt> and a double and returns an <tt>int</tt> to accommodate to
     * specific use cases that turn out to be common. Note that this method just performs a call to
     * {@link #apply(double, double)} and rounds the result.
     * 
     * @param a
     *            First operand
     * @param b
     *            Second operand
     * @return Result of the operation
     */
    public int apply (int a, double b) {
        return (int) Math.round(apply((double) a, b));
    }

    /**
     * Returns an <tt>Operator</tt> based on its symbol.
     * 
     * @param symbol
     *            String representation of the operator
     * @return The corresponding operator
     */
    public static Operator get (String symbol) {
        return OPERATORS.get(symbol);
    }

    @Override
    public String toString () {
        return string;
    }
}
