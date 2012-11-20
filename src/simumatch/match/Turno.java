package simumatch.match;

/**
 * Each of the turns of the game
 */
public class Turno {
	private String comentarista = "Turno ";
	private int numeroDeTurno;
	private int estado= 7;
	private String local, visitante;
	private Partido partido;
	
	/** @return The state of this turn */
	public int getEstado() {
		return estado; //el estado resultado
	}
	
	@Override
	public String toString(){
		return comentarista; //el resumen del turno
	}
	
	// Constructora: No es publica, si necesitas crear turnos pidelos a Partido.turno(..)
	Turno(int estado, Partido p, double aba[]){
		this.estado = estado;
		this.partido = p;
		local= p.local.getName();
		visitante= p.visitante.getName();
		numeroDeTurno = partido.turnoActual;

		if(numeroDeTurno==partido.descanso)comentarista = "Descanso ("+numeroDeTurno+") ";
		else comentarista += numeroDeTurno+": ";
		
		comentarista+="[Resultado: "+estado+"]\n";
		
		switch(estado){
		case  6 : com("¡Gol de "+ local+"!");break;
		case  5 : com(local+" esta dominado el juego, es cuestion de tiempo que marque");break;
		case  1 : com(local+" tiene oportunidad de tomar la iniciativa");break;
		case  0 : com("El partido esta muy igualado");break;
		case -1 : com(visitante+" tiene oportunidad de tomar la iniciativa");break;
		case -5 : com(visitante+" esta dominado el juego, es cuestion de tiempo que marque");break;
		case -6 : com("¡Gol de "+ visitante+"!");break;
		default:
			if(estado >6 || estado <-6){
				System.out.println("Se ha llegado un estado incoherente: "+estado);
				com("Parece que unos aliens han desembarcado en medio del campo...Yo me voy a mi casa!!!");
				return;
			}
			if(estado>0) com(local+" lleva la iniciativa");
			else 	 com(visitante+" lleva la iniciativa");
		}
		
		if(partido.animoL>2*partido.animoV)com("Parece que "+local+" esta mucho mas animado que "+visitante);
		else if(partido.animoV>2*partido.animoL)com("Parece que "+visitante+" esta mucho mas animado que "+local);
		
		if(estado>0){
			if(partido.tacL==2)com("Parece que "+local+" no esta aprovechando su ventaja, si yo fuera el entrenador me enfadaria mucho");
			if(partido.tacV==1)com("Parece que "+visitante+" no esta defendiendo debidamente, es una oportunidad magnifica para marcar");
		}else if(estado<0){
			if(partido.tacV==2)com("Parece que "+visitante+" no esta aprovechando su ventaja, si yo fuera el entrenador me enfadaria mucho");
			if(partido.tacL==1)com("Parece que "+local+" no esta defendiendo debidamente, es una oportunidad magnifica para marcar");
		}
		
		comentarista+=escribeAbanico(aba);
		if(numeroDeTurno==1)comentarista+="\n[Ventaja Local <<<------------------------->>> Ventaja Visitante]\n";
		
		if(numeroDeTurno==partido.duracion){
			com("¡Final del partido!");
			if(partido.goles()==0)com("EMPATE");
			else if(partido.goles()> 0)com("Ganador: el equipo local: "+local);
			else com("Ganador: el equipo visitante: "+visitante);
		}
		
		comentarista+="\nMarcador: ["+partido.marL+"|"+partido.marV+"]\n\n\n";
		
		if(numeroDeTurno==partido.descanso-1)
			comentarista+="Los jugadores se retiran del campo:\nComienza el DESCANSO\n\n";
	}
	void com(String comentario){
		comentarista+="\n"+comentario+".";
	}
	private static String escribeAbanico(double[] aba) {
		String r = "\n[";
		for (int i=aba.length;;){
			r+=porcen2Char(aba[--i])+'%';
			if(i>0)r+='|';
			else return r+']'; 
		}
	}
	private static String porcen2Char(double prob) {
		int n = Math.round((float)(100*Math.abs(prob)));
		if(n<10)return " "+n;
		return Integer.toString(n);
	}
}
