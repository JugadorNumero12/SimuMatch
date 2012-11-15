package simumatch.datamanager;

/**
 * An action that can be performed by a team in a match.
 */
public enum Action {
	CALDEAR_AMBIENTE, OLA, PANCARTA, PITAR, SOBORNAR, APOSTAR, DOBLAR_APUESTA, EVENTO_BENEFICO, ENTREVISTA;
	
	/**
	 * @param name
	 *            The name of the action to retrieve
	 * @return The <tt>Action</tt> associated with the given <tt>name</tt>, or <tt>null</tt> if there is no such
	 *         <tt>Action</tt>
	 */
	public static Action get ( String name ) {
		try {
			return Enum.valueOf( Action.class, name.trim().toUpperCase() );
		} catch ( IllegalArgumentException exc ) {
			return null;
		}
	}
}
