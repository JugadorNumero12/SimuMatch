package simumatch.datamanager;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the "DataBase" and implements methods in order to:
 * - acces de data
 * - fill the data
 * 
 * The data would be represented as a HasMap<Action, ArrayList<Effect>>
 * -----------------------------------------------------
 * | ACTION1 | Effect1 Effect2 Effect3 |
 * | ACTION2 | Effect1 |
 * | ACTION3 | Effect1 Effect2 |
 * | ACTION4 | Effect1 Effect2 Effect3 |
 * | ACTION5 | Effect1 |
 * | ACTION6 | Effect1 Effect2 Effect3 Effect4 |
 * -----------------------------------------------------
 * 
 */
public class AbilitiesData {
	
	/** The data collected */
	private final Map<Action,Collection<Effect>> data = new HashMap<Action,Collection<Effect>>();
	
	/**
	 * Reads a file and adds its information to this object
	 * 
	 * @param file
	 *            The file to read from
	 * @throws IOException
	 *             if anything goes wrong
	 */
	public void loadFile ( File file ) throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader( file ) );
		
		boolean eof = false;
		while ( !eof ) {
			Action action = readAction( reader );
			if ( action == null ) {
				eof = true;
				
			} else {
				Collection<Effect> effects = new ArrayList<Effect>();
				
				Effect effect;
				while ( ( effect = readEffect( reader ) ) != null ) {
					effects.add( effect );
				}
				
				addAction( action, Collections.unmodifiableCollection( effects ) );
			}
		}
	}
	
	/**
	 * Adds an action to this object
	 * 
	 * @param action
	 *            The action to add
	 * @param effects
	 *            The effect to add
	 */
	public void addAction ( Action action, Collection<Effect> effects ) {
		if ( action == null ) {
			throw new NullPointerException( "action" );
		}
		if ( effects == null || effects.contains( null ) ) {
			throw new NullPointerException( "effects" );
		}
		
		data.put( action, Collections.unmodifiableCollection( new ArrayList<Effect>( effects ) ) );
	}
	
	public Map<Action,Collection<Effect>> getData () {
		return Collections.unmodifiableMap( new HashMap<Action,Collection<Effect>>( data ) );
	}
	
	/**
	 * @param reader
	 *            The object used to read information
	 * @return An action read from the <tt>reader</tt>
	 * @throws IOException
	 *             If anything goes wrong
	 */
	private Action readAction ( BufferedReader reader ) throws IOException {
		String line = reader.readLine();
		
		// If the line is null, EOF -- return null to signal that
		if ( line == null ) {
			return null;
		}
		
		String name = line.trim();
		Action action = Action.get( name );
		if ( action == null ) {
			throw new IOException( "'" + name + "' is not a valid action name" );
		}
		
		return action;
	}
	
	/**
	 * @param reader
	 *            The object used to read information
	 * @return An effect read from the <tt>reader</tt>
	 * @throws IOException
	 *             If anything goes wrong
	 */
	private Effect readEffect ( BufferedReader reader ) throws IOException {
		String line = reader.readLine();
		
		// If line is null, the file is over -- return null to signal that
		if ( line == null ) {
			return null;
		}
		
		// If line is empty, the list of strings is over -- return null to signal that
		String effstr = line.trim();
		if ( ( "" ).equals( effstr ) ) {
			return null;
		}
		
		// Split the line in parts: <scope> <op&bonus> <target> <perm>
		String[] parts = effstr.split( "\\s+" );
		if ( parts.length != 4 ) {
			throw new IOException( "Invalid effect format" );
		}
		
		// Parse everything -- these methods throw an exception when necessary
		Scope scope = parseScope( parts[ 0 ] );
		Operator op = parseOperator( parts[ 1 ].substring( 0, 1 ) );
		double bonus = parseBonus( parts[ 1 ].substring( 1 ) );
		Target target = parseTarget( parts[ 2 ] );
		boolean perm = parsePermanent( parts[ 3 ] );
		
		// Return the effect at last
		return new Effect( scope, target, op, bonus, perm );
	}
	
	/**
	 * @param str
	 *            String to parse
	 * @return A <tt>Scope</tt> object read from <tt>str</tt>
	 * @throws IOException
	 *             If the format is invalid
	 */
	private static Scope parseScope ( String str ) throws IOException {
		Scope scope = Scope.get( str );
		if ( scope == null ) {
			throw new IOException( "Invalid scope name" );
		}
		
		return scope;
	}
	
	/**
	 * @param str
	 *            String to parse
	 * @return An <tt>Operator</tt> object read from <tt>str</tt>
	 * @throws IOException
	 *             If the format is invalid
	 */
	private static Operator parseOperator ( String string ) throws IOException {
		Operator op = Operator.get( string );
		if ( op == null ) {
			throw new IOException( "Invalid operator '" + string + "'" );
		}
		
		return op;
	}
	
	/**
	 * @param str
	 *            String to parse
	 * @return A <tt>Target</tt> object read from <tt>str</tt>
	 * @throws IOException
	 *             If the format is invalid
	 */
	private static Target parseTarget ( String string ) throws IOException {
		Target target = Target.get( string );
		if ( target == null ) {
			throw new IOException( "Invalid target '" + string + "'" );
		}
		
		return target;
	}
	
	/**
	 * @param str
	 *            String to parse
	 * @return A <tt>boolean</tt> value read from <tt>str</tt>
	 * @throws IOException
	 *             If the format is invalid
	 */
	private static boolean parsePermanent ( String string ) throws IOException {
		boolean perm;
		if ( "perm".equals( string ) || "permanent".equals( string ) ) {
			perm = true;
			
		} else if ( "temp".equals( string ) || "temporary".equals( string ) ) {
			perm = false;
			
		} else {
			throw new IOException( "Invalid permanent declaration '" + string + "'" );
		}
		
		return perm;
	}
	
	/**
	 * @param str
	 *            String to parse
	 * @return A <tt>double</tt> value read from <tt>str</tt>
	 * @throws IOException
	 *             If the format is invalid
	 */
	private static double parseBonus ( String string ) throws IOException {
		try {
			return Double.parseDouble( string );
		} catch ( NumberFormatException exc ) {
			throw new IOException( exc );
		}
	}
	
}
