package simumatch.common;

/**
 * The target team of an <tt>Effect</tt>.
 */
public enum Target {
    SELF,
    OPPONENT,
    BOTH;

    /**
     * Returns a <tt>Target</tt> based on its name. The given <tt>name</tt> is case-insensitive and will be
     * {@link java.lang.String#trim trimmed}.
     * 
     * @param name
     *            The name of the target to retrieve
     * @return The <tt>Target</tt> associated with the given <tt>name</tt>, or <tt>null</tt> if there is no such
     *         <tt>Target</tt>
     */
    public static Target get (String name) {
        try {
            return Enum.valueOf(Target.class, name.trim().toUpperCase());
        } catch (IllegalArgumentException exc) {
            return null;
        }
    }
}
