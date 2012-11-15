package simumatch.datamanager;

public enum Scope {
	ATMOSPHERE,
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
			return Enum.valueOf( Scope.class, name );
		} catch ( IllegalArgumentException exc ) {
			return null;
		}
	}
}
