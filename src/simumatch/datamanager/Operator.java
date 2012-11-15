package simumatch.datamanager;

/** An operator used to calculate bonuses in <tt>Effect</tt>s */
public enum Operator {
	/** Arithmetic addition */
	ADDITION {
		
		@Override
		double apply ( double a, double b ) {
			return a + b;
		}
	},
	
	/** Arithmetic subtraction */
	SUBTRACTION {
		
		@Override
		double apply ( double a, double b ) {
			return a - b;
		}
	},
	
	/** Arithmetic product */
	PRODUCT {
		
		@Override
		double apply ( double a, double b ) {
			return a * b;
		}
	};
	
	/**
	 * Applies the operator to the given operands
	 * 
	 * @param a
	 *            First operand
	 * @param b
	 *            Second operant
	 * @return Result of the operation
	 */
	abstract double apply ( double a, double b );
}
