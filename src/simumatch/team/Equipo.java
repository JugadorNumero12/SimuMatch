package simumatch.team;

import simumatch.common.Effect;
import java.util.List;

/**
 * A team thatcan play a {@link simumatch.match.Partido match}.
 */
public class Equipo {
	/** @return A test team */
	public static Equipo equipo_de_prueba1(){
		Equipo e = new Equipo("Optimistas");
		e.nivel=4;
		e.orgullo=150;
		e.versatilidad = 0.8;
		e.indiceDefensivo = 1.5;
		e.indiceOfensivo = 2;
		e.indiceOptimismo = 15;
		e.indiceFrialdad = 15;
		return e;
	}
	
	/** @return A test team */
	public static Equipo equipo_de_prueba2(){
		Equipo e = new Equipo("Agresivos");
		e.nivel=1;
		e.orgullo=100;
		e.versatilidad = 0.8;
		e.indiceDefensivo = 1.5;
		e.indiceOfensivo = 3;
		e.indiceOptimismo = 8;
		e.indiceFrialdad = 8;
		return e;
	}
	
	int nivel;//el nivel de juego medio del equipo
	double versatilidad;//probabilidad de cambiar de estrategia a la optima, depende del entrenador
	double indiceOfensivo;//multiplicador al abanico cuando llebas ventaja
	double indiceDefensivo;//multiplicador al abanico cuando vas perdiendo
	double indicePosicional;//multiplicador al avanico para estabilizar el juego
	double indiceOportunista;//multiplicador al avanico para desestabilizar el juego
	double indiceContraOf;//multiplicador a la inversion del avanico
	double indiceOptimismo;//multiplicador al incremento de animo
	double indiceFrialdad;//multiplicador al decremento de animo
	double indiceViloencia;//probablidad de juego sucio
	int orgullo;//animo base al empezar el partido
	/** Stadium of the team */
	public Estadio estadio;
	int aforoBase;
	private List<Effect> preparacion;//las acciones preparadas para el proximo partido
	private String nombre;
	
	/**
	 * @param nombre
	 *            Name of the team.
	 * @param lvl
	 *            Level of the team.
	 * @param estadio
	 *            Stadium of the team.
	 * @param aforoBase
	 *            Basecapacity of the team.
	 * @throws IllegalArgumentException
	 *            if <tt>lvl</tt> is zero or negative or <tt>aforoBase</tt> is negative
	 */
	public Equipo(String nombre, int lvl, Estadio estadio, int aforoBase) {
		if(lvl<1||aforoBase<0)throw new IllegalArgumentException("Datos incorrectos: Equipo no puede tener nivel ni aforo negativo");
		this.nombre = nombre;
		this.estadio = estadio;
		this.nivel = lvl;
		this.aforoBase = aforoBase;
	}
	
	/**
	 * Creates a team with default arguments.
	 * <p>
	 * This is equivalent to a call to <tt>new Equipo(nombre, 1, new Estadio(), 100)</tt>.
	 * 
	 * @param nombre
	 *         Name of the team
	 */
	public Equipo(String nombre){
		this( nombre, 1, new Estadio(), 100 );
	}
	
	/** @return Base capacity o this team */
	public int aforoBase(){
		return aforoBase;
	}
	
	/** Resets the preparatory effects */
	public void resetPreparatorias(){
		preparacion = null;
	}
	
	/** @return Level of this team */
	public int nivel(){
		return nivel;//el nivel de juego medio del equipo
	}
	
	/** @return Versatility of this team */
	public double versatilidad(){
		return versatilidad;
	}
	
	/** @return Offensive value of this team */
	public double indiceOfensivo(){
		return indiceOfensivo;
	}
	
	/**
	 * Sets the offensive value of this team
	 * 
	 * @param IndOf
	 *            New offensive value
	 */
	public void setIOf(double IndOf){
		indiceOfensivo=IndOf;
	}
	
	/** @return Deffensive value of this team */
	public double indiceDefensivo(){
		return indiceDefensivo;
	}
	
	/**
	 * Sets the deffensivevalue of this team
	 * 
	 * @param IndDf
	 *            New deffensive value
	 */
	public void setIDf(double IndDf){
		indiceDefensivo=IndDf;
	}
	
	// FIXME
	// WHAT IS THIS I DON'T EVEN
	
	/** @return Positional value of this team */
	public double indicePosicional(){
		return indicePosicional;
	}
	
	/** @return Opportunism value of this team */
	public double indiceOportunista(){
		return indiceOportunista;
	}
	
	/** @return Counter-offensive value of this team */
	public double indiceContraOf(){
		return indiceContraOf;
	}
	
	/** @return Optimism value of this team */
	public double indiceOptimismo(){
		return indiceOptimismo;
	}
	
	/** @return Coldness value of this team */
	public double indiceFrialdad(){
		return indiceFrialdad;}
	
	
	/** @return Violence value of this team */
	public double indiceViloencia(){
		return indiceViloencia;
	}
	
	/** @return Proud of this team */
	public int orgullo(){
		return orgullo;
	}
	
	// END OF WHAT IS THIS I DON'T EVEN
	
	/** @return Name of this team */
	public String getName(){
		return nombre;
	}
	
	/**
	 * Sets the list of preparatory effects for this team
	 * @param accionesPreparatorias
	 *            New preparatory effects
	 */
	public void setPerparatorias(List<Effect> accionesPreparatorias){
		preparacion = accionesPreparatorias;
	}
	
	/** @return The list of preparatory effects of this team */
	public List<Effect> getPreparatorias() {
		return preparacion;
	}
	

}
