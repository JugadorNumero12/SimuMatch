package simumatch.common;

public enum Scope {
	ATMOSPHERE,//TODO esto es MUY confuso
	TEAM_LEVEL,
	PEOPLE,
	ENCOURAGE,
	OFFENSIVE_SPIRIT,
	DEFENSIVE_SPIRIT;
	
	/**
	 * @param name
	 *            The name of the scope to retrieve
	 * @return The <tt>Scope</tt> associated with the given <tt>name</tt>, or <tt>null</tt> if there is no such
	 *         <tt>Scope</tt>
	 */
	public static Scope get ( String name ) {
		try {
			return Enum.valueOf( Scope.class, name.trim().toUpperCase() );
		} catch ( IllegalArgumentException exc ) {
			return null;
		}
	}
}
