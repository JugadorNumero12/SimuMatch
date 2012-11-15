package simumatch.datamanager;

public final class Effect {
	
	/** Which attribute this effect affects */
	private final Scope scope;
	
	/** The target of the effect */
	private final Target target;
	
	/** The numeric value of this effect */
	private final double bonus;
	
	/** Operator this effect uses */
	private final Operator operator;
	
	/** Whether it lasts the whole match */
	private final boolean permanent;
	
	/**
	 * @param scope
	 *            Attribute this effect affects
	 * @param target
	 *            Target of the effect
	 * @param operator
	 *            Operator used on the target's scope
	 * @param bonus
	 *            Numeric value of the effect
	 * @param permanent
	 *            Whether it lasts the whole match
	 */
	public Effect ( Scope scope, Target target, Operator operator, double bonus, boolean permanent ) {
		if ( scope == null ) {
			throw new NullPointerException( "scope" );
		}
		if ( target == null ) {
			throw new NullPointerException( "target" );
		}
		if ( operator == null ) {
			throw new NullPointerException( "operator" );
		}
		if ( bonus < 0 ) {
			throw new IllegalArgumentException( "bonus < 0 (" + bonus + ")" );
		}
		
		this.scope = scope;
		this.target = target;
		this.bonus = bonus;
		this.operator = operator;
		this.permanent = permanent;
	}
	
	/** @return Which attribute this effect affects */
	public Scope getScope () {
		return scope;
	}
	
	/** @return The target of the effect */
	public Target getTarget () {
		return target;
	}
	
	/** @return Numeric value of this effect */
	public double getBonus () {
		return bonus;
	}
	
	/** @return Operator this effect uses */
	public Operator getOperator () {
		return operator;
	}
	
	/** @return Whether it lasts the whole match */
	public boolean isPermanent () {
		return permanent;
	}
	
	@Override
	public int hashCode () {
		int hc = 0;
		
		hc += scope.hashCode();
		hc *= 17;
		
		hc += target.hashCode();
		hc *= 17;
		
		hc += (int) ( bonus * 100 );
		hc *= 17;
		
		hc += operator.hashCode();
		hc *= 17;
		
		hc += permanent ? 3 : 1;
		hc *= 17;
		
		return hc;
	}
	
	@Override
	public boolean equals ( Object obj ) {
		if ( !( obj instanceof Effect ) ) {
			return false;
		}
		
		Effect effect = (Effect) obj;
		return scope.equals( effect.scope ) && target.equals( effect.target ) && bonus == effect.bonus
			&& operator.equals( effect.operator ) && permanent == effect.permanent;
	}
	
	public String toString() {
		return "Effect(" + scope + "," + operator + bonus + "," + target + "," + (permanent?"PERM":"TEMP") + ")"; 
	};
}
