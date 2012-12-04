package simumatch.common;

/**
 * The attribute of a match that an <tt>Effect</tt> affects to.
 */
public enum Scope {
    ATMOSPHERE,
    TEAM_LEVEL,
    PEOPLE,
    ENCOURAGE,
    OFFENSIVE_SPIRIT,
    DEFENSIVE_SPIRIT;

    /**
     * Returns a <tt>Scope</tt> based on its name. The given <tt>name</tt> is case-insensitive and will be
     * {@link java.lang.String#trim trimmed}.
     * 
     * @param name
     *            The name of the scope to retrieve
     * @return The <tt>Scope</tt> associated with the given <tt>name</tt>, or <tt>null</tt> if there is no such
     *         <tt>Scope</tt>
     */
    public static Scope get (String name) {
        try {
            return Enum.valueOf(Scope.class, name.trim().toUpperCase());
        } catch (IllegalArgumentException exc) {
            return null;
        }
    }
}
