package dataManager;

/**
 * An Effect works as an c-struct with the following fields:
 * scope: which factor affects, there are 6 possibilities:
 * ATMOSPHERE, TEAM_LEVEL, PEOPLE, ENCOURAGE, OFFENSIVE_SPIRIT, DEFFENSIVE_SPIRIT
 * objective: it could affects our team (true), or the enemy team (false)
 * bonus: a double (the force of the effect)
 * operator: if we must sum (+) the bonus or multiply (*) the bonus
 */
public class Effect {
	
	private final Scope scope;
	private final Target target;
	private final double bonus;
	private final boolean operator;
	private final boolean permanent;
	
	public Effect ( Scope scope, Target target, double bonus, boolean operator, boolean permanent ) {
		if ( scope == null ) {
			throw new NullPointerException( "scope" );
		}
		if ( target == null ) {
			throw new NullPointerException( "target" );
		}
		
		this.scope = scope;
		this.target = target;
		this.bonus = bonus;
		this.operator = operator;
		this.permanent = permanent;
	}
}
