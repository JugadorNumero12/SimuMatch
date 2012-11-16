package simumatch.datamanager;

import java.util.Comparator;

import simumatch.common.Effect;
import simumatch.common.Operator;

/**
 * Compares <tt>Effect</tt>s so products appear before additions and subtractions
 */
public final class EffectComparator implements Comparator<Effect> {
	
	@Override
	public int compare ( Effect effect1, Effect effect2 ) {
		if ( effect1 == null || effect2 == null ) {
			throw new NullPointerException();
		}
		
		return compareOperators( effect1.getOperator(), effect2.getOperator() );
	}
	
	private static int compareOperators ( Operator op1, Operator op2 ) {
		if ( op1 == Operator.PRODUCT && op2 != Operator.PRODUCT ) {
			return -1;
		} else if ( op1 != Operator.PRODUCT && op2 == Operator.PRODUCT ) {
			return 1;
		} else {
			return 0;
		}
	}
}
