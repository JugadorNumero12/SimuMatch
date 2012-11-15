package simumatch.team;

public class Estadio {
	int capacidad = 300;
	public int getAforoEqV(){return capacidad/2;}
	public int getAforoEqL(){return capacidad/2;}
	void setCapacidad(int capacidad){
		this.capacidad = capacidad;
	}
}
