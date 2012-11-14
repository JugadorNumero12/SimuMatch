package dataManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the "DataBase" and implements methods in order to:
 *  - acces de data
 *  - fill the data 
 *  
 * 	The data would be represented as a HasMap<Action, ArrayList<Effect>>
 *  -----------------------------------------------------
 *  |  ACTION1 	| Effect1	Effect2	 Effect3			|
 *  |  ACTION2 	| Effect1                    			|
 *  |  ACTION3 	| Effect1	Effect2	 					|
 *  |  ACTION4 	| Effect1	Effect2	 Effect3			|
 *  |  ACTION5 	| Effect1								|
 *  |  ACTION6 	| Effect1	Effect2	 Effect3  Effect4	|
 *  ----------------------------------------------------- 
 *
 */
public class AbilitiesData
{
	private HashMap<Actions, ArrayList<Effect>> data;
	
	public AbilitiesData()
	{
		data = new HashMap<Actions, ArrayList<Effect>>();
	}
	
	/** 
	 * this method will read from 3 files (ultra.txt, animadora.txt, empreario.txt) 
	 * and will fill the data structure.
	 * 
	 * @param the URL of the first file
	 * @param the URL of the second file
	 * @param the URL of the third file 
	 */
	public void fillData(String f1, String f2, String f3)
	{		
		int fileNumber = 0;
		while(fileNumber < 2){
	
			File f = new File(f1);
			
			/*  Read each file  */
			
			readFile(f);
			
		}
	}
	
	/** 
	 * Each file will have this structure: 
	 *  - a String (the name of the action) MUST corresponds to an action in the Enum Actions
	 *  - the string 'ambiente' followed by a double (example 0.03)
	 *  - the string 'nivel_equipo' followed by a double
	 *  - the string 'aforo' followed by a double
	 *  - the string 'animo' followed by a double
	 *  - the string 'factor_ofensivo' followed by a double
	 *  - the string 'factor_defensivo' followed by a double
	 *  - a blanck line
	 *  
	 *  @param the file
	 */
	private void readFile(File file)
	{
		try {
			String line = "";
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			while(line != null){
				
				/*  Read file  */
				
				/*  There is an example:
				 *  ----------------------------- ultra.txt
				 *	CALDEAR_AMBIENTE
				 *	ambiente +4
				 *	nivel_equipo 0
				 *	aforo 0,02
				 *	animo -0,03
				 *	factor_ofensivo 0
				 *	factor_defensivo 0
				 *	
				 *	OLA
				 *	ambiente +4
				 *	nivel_equipo 0
				 *	aforo 0,02
				 *	animo -0,03
				 *	factor_ofensivo 0
				 *	factor_defensivo 0
				 *	
				 *	(...)
				 *  -----------------------------
				 */
			}
			
		}
		catch(Exception e){
			/*  process exceptions  */
		}
	}
		
	/** Returns the arrayList of effects of the asked action and null 
	 * if the action doesn't exist
	 * 
	 * @param the action's name
	 * @returns null if not founded, the effects list otherwise
	 */
	public ArrayList<Effect> getActions(String actionName)
	{
		
		/* Search for abilityName in data*/
	
		return null;
	}
	
	/**
	 * 
	 * @param teamA
	 * @param teamB
	 * @returns 
	 */
	public GlobalEffect[] proccessActions(ArrayList<Actions> teamA, ArrayList<Actions> teamB)
	{
		
	}

}
