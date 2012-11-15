package simumatch.match;

public class Turno {
	String comentarista = "Turno ";
	int numeroDeTurno;
	public int estado;
	String local, visitante;
	Partido partido;
	Turno(Partido p){
		local= p.local.nombre;
		visitante=p.visitante.nombre;
		estado = p.equilibrio;
		numeroDeTurno = 1;
	}
	Turno(int estado){
		this.estado = estado;
		numeroDeTurno = partido.turnoActual;

		if(partido.turnoActual==partido.duracion/2)comentarista = "Descanso("+numeroDeTurno+") ";
		else comentarista += numeroDeTurno+": ";
	
		if(estado>5)com("�Gol de"+ local+"!");
		if(estado<1)com("�Gol de"+ visitante+"!");
		comentarista+="\n["+partido.marL+"|"+partido.marV+"]\n\n";
	}
	public String toString(){
		return comentarista;
	}
	void com(String comentario){
		comentarista+="\n"+comentario+".";
	}
}
