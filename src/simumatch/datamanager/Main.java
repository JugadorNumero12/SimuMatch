package simumatch.datamanager;


import java.util.ArrayList;
@SuppressWarnings("unused")


public class Main {
	
	public static void main (String[] args) {
		AbilitiesData a = new AbilitiesData();
		a.fillData("./ultra.txt", "./animadora.txt", "./empresario.txt");

		// The GraphicInterface give us this information
		/*ArrayList<Action> teamA = new ArrayList<Action>();
		teamA.add(Action.OLA);
		teamA.add(Action.PANCARTA);
		teamA.add(Action.SOBORNAR);
		
		ArrayList<Action> teamB = new ArrayList<Action>();
		teamB.add(Action.OLA);
		teamB.add(Action.DOBLAR_APUESTA);*/
			
		// We will provide the Engine with the processed effects of each team
	
		//GlobalEffect[2] g = a.processActions(teamA, teamB);
		
	}
}
