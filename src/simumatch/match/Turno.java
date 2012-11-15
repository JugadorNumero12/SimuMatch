package simumatch.match;

public class Turno {
	String comentarista = "Turno ";
	int numeroDeTurno;
	public int estado;
	String local, visitante;
	Partido partido;
	Turno(Partido p){
		this.partido = p;
		local= p.local.nombre;
		visitante=p.visitante.nombre;
		estado = p.equilibrio;
		numeroDeTurno = partido.turnoActual;
	}
	Turno(int estado, Partido p){
		this.estado = estado;
		this.partido= p;
		numeroDeTurno = partido.turnoActual;

		if(partido.turnoActual==partido.duracion/2)comentarista = "Descanso("+numeroDeTurno+") ";
		else comentarista += numeroDeTurno+": ";
		
		switch(estado){
		case  6 : com("¡Gol de"+ local+"!");break;
		case  5 : com(local+" esta dominado el juego, es cuestion de tiempo que marque");break;
		case  1 : com(local+" tiene oportunidad de tomar la iniciativa");break;
		case  0 : com("El partido esta muy igualado");break;
		case -1 : com(visitante+" tiene oportunidad de tomar la iniciativa");break;
		case -5 : com(visitante+" esta dominado el juego, es cuestion de tiempo que marque");break;
		case -6 : com("¡Gol de"+ visitante+"!");break;
		default:
			if(estado >6 || estado <-6){
				System.out.println("Se ha llegado un estado incoherente: "+estado);
				com("Parece que unos aliens han desembarcado en medio del campo...Yo me voy a mi casa!!!");
				break;
			}
			if(estado>0) com(local+" lleva la iniciativa");
			else 	 com(visitante+" lleva la iniciativa");
		}
		
		comentarista+="\n["+partido.marL+"|"+partido.marV+"]\n\n";
	}
	public String toString(){
		return comentarista;
	}
	void com(String comentario){
		comentarista+="\n"+comentario+".";
	}
}
