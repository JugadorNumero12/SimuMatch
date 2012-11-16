package simumatch.datamanager;

import java.util.Comparator;

import simumatch.common.Effect;
import simumatch.common.Operator;

/**
 * Compares <tt>Effect</tt>s so products appear before additions and subtractions
 */
public class EffectComparator implements Comparator<Effect> {
	
	@Override
	public int compare ( Effect effect1, Effect effect2 ) {
		if ( effect1 == null || effect2 == null ) {
			throw new NullPointerException();
		}
		
		if ( effect1.getOperator() == Operator.PRODUCT && ( effect2.getOperator() != Operator.PRODUCT ) ) {
			return -1;
		} else if ( ( effect1.getOperator() != Operator.PRODUCT && ( effect2.getOperator() == Operator.PRODUCT ) ) ) {
			return 1;
		} else {
			return 0;
		}
		
	}
}
