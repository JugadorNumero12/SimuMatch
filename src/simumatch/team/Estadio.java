package simumatch.team;

public class Estadio {
	int capacidad = 300;
	public int getAforoV(){return capacidad/2;}
	public int getAforoL(){return capacidad/2;}
	void setCapacidad(int capacidad){
		this.capacidad = capacidad;
	}
}
