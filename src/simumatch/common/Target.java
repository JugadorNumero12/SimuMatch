package simumatch.common;

public enum Target {
	SELF, OPPONENT, BOTH;
	
	/**
	 * @param name
	 *            The name of the target to retrieve
	 * @return The <tt>Target</tt> associated with the given <tt>name</tt>, or <tt>null</tt> if there is no such
	 *         <tt>Target</tt>
	 */
	public static Target get ( String name ) {
		try {
			return Enum.valueOf( Target.class, name.trim().toUpperCase() );
		} catch ( IllegalArgumentException exc ) {
			return null;
		}
	}
}
