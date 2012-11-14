package dataManager;

import java.util.ArrayList;


public class Main {
	
	public static void main(String[] args){
		AbilitiesData a = new AbilitiesData();
		a.fillData("./ultra.txt", "./animadora.txt", "./empresario.txt");

		// The GraphicInterface give us this information
		ArrayList<Actions> teamA = new ArrayList<Actions>();
			teamA.add(Actions.OLA);
			teamA.add(Actions.PANCARTA);
			teamA.add(Actions.SOBORNAR);
		ArrayList<Actions> teamB = new ArrayList<Actions>();
			teamB.add(Actions.OLA);
			teamB.add(Actions.DOBLAR_APUESTA);
			
		// We will provide the Engine with the processed effects of each team
	
		GlobalEffect[2] g = a.processActions(teamA, teamB);
		
	}
}
