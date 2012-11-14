package dataManager;

/**
 * An Effect works as an c-struct with the following fields:
 * 	scope: 		which factor affects, there are 6 possibilities:
 * 				ATMOSPHERE, TEAM_LEVEL, PEOPLE, ENCOURAGE, OFFENSIVE_SPIRIT, DEFFENSIVE_SPIRIT
 *  objective: 	it could affects our team (true), or the enemy team (false)
 *  bonus: 		a double (the force of the effect)
 *  operator: 	if we must sum (+) the bonus or multiply (*) the bonus 
 */
public class Effect {
	public Scope scope; 
	public boolean objetive; 
	public double bonus; 
	public boolean operator;
	
	public Effect(Scope scope, boolean objetive, double bonus, boolean operator){
		this.scope = scope;
		this.objetive = objetive;
		this.bonus = bonus;
		this.operator = operator;
	}
}
