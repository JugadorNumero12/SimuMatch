package simumatch.datamanager;

import java.util.Comparator;

import simumatch.common.Effect;
import simumatch.common.Operator;


public final class EffectComparator implements Comparator<Effect> {

	@Override
	public int compare ( Effect effect1, Effect effect2 ) {
		if ( effect1 == null || effect2 == null ) {
			throw new NullPointerException();
		}
	int aux = compareOperators( effect1.getOperator(), effect2.getOperator() );
	if(aux != 0)return aux;
	aux = compareScope(effect1.getScope(), effect2.getScope() );
	if(aux != 0) return aux;
	aux = compareTarget (effect1.getTarget(), effect2.getTarget() );
	if(aux != 0) return aux;
	aux = private static int compareBonus( effect1.getBonus() , effect2.getBonus() );
	if( aux != 0 )	return aux;
	return comparePermanent( effect1.isPermanent() effect2.isPermanent() );
	
}
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
	
	private static int compareScope( Scope sp1, Scope sp2 ){
		return sp1.compareTo(sp2);
	}

	private static int compareTarget( Target tg1, Target tg2 ){
		return tg1.compareTo(tg2);

	}
	
	private static int compareBonus( Double db1, Double db2 ){
		return db1.compareTo(db2);

	}

	private static int comparePermanent( Boolean bl1, Boolean bl2 ){
		return bl1.compareTo(bl2);

	}
}
