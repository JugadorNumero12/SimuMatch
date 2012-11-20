package simumatch.common;

/**
 * An action that can be performed by a team in a match.
 * <p>
 * This class is actually just an enumeration of all possible actions with their names. Effects of these <tt>Action</tt>
 * s are represented by the {@link Effect} class, and should be loaded from files using an
 * {@link simumatch.datamanager.AbilitiesData} object.
 */
public enum Action {
	// --- Preparation ---
	MOTIVARSE, PINTARSE, PELEA_AFICIONES, CREAR_PANCARTA, PROMOCIONAR_EQUIPO, HACKEAR_PAGINA, ORGANIZAR_CENA,
	ORGANIZAR_HOMENAJE, CONTRATAR_RRPP, FINANCIAR_EVENTO, MEJORAR_GRADAS, SOBORNAR_LINIER, INCENTIVO_ECONOMICO,
	ASCENDER_TRABAJO, APOSTAR,
	
	// --- In-match ---
	SALTO_ESPONTANEO, INICIAR_OLA, PUNTERO_LASER, TIRAR_BENGALA, BEBER_CERVEZA, ENTREVISTA_INTERMEDIO,
	RETRANSMITIR_PARTIDO, HABLAR_SPEAKER, ACTIVAR_SOBORNO, DOBLAR_APUESTA;
	
	/**
	 * Returns an <tt>Action</tt> based on its name. The given <tt>name</tt> is case-insensitive and will be
	 * {@link java.lang.String#trim trimmed}.
	 * 
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
