package simumatch.team;

/**
 * A stadium of a particular team.
 */
public class Estadio {
	int capacidad = 300;
	
	/** @return Capacity of the visitor team */
	public int getAforoV(){return capacidad/2;}
	
	/** @return Capacity of the local team */
	public int getAforoL(){return capacidad/2;}
	
	void setCapacidad(int capacidad){
		this.capacidad = capacidad;
	}
}
