package simumatch.team;

import simumatch.common.Effect;
import java.util.List;

public class Equipo {
	public static Equipo equipo_de_prueba1(){
		Equipo e = new Equipo("Optimistas");
		e.nivel=2;
		e.orgullo=88;
		e.versatilidad = 0.8;
		e.aforoBase= 100;
		e.indiceDefensivo = 1.5;
		e.indiceOfensivo = 1.5;
		e.indiceOptimismo = 15;
		e.indiceFrialdad = 15;
		return e;
	}
	public static Equipo equipo_de_prueba2(){
		Equipo e = new Equipo("Ofensivos");
		e.nivel=2;
		e.orgullo=100;
		e.versatilidad = 0.8;
		e.aforoBase= 90;
		e.indiceDefensivo = 1.5;
		e.indiceOfensivo = 4;
		e.indiceOptimismo = 6;
		e.indiceFrialdad = 3;
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
	public Estadio estadio;
	public String nombre;
	int aforoBase;
	public List<Effect> preparacion;//las acciones preparadas para el proximo partido
	
	public Equipo(String nombre, int lvl, Estadio estadio, int aforoBase) throws Exception{
		if(lvl<1||aforoBase<0)throw new Exception("Datos incorrectos: Equipo no puede tener nivel ni aforo negativo");
		this.nombre = nombre;
		this.estadio = estadio;
		this.nivel = lvl;
		this.aforoBase = aforoBase;
	}
	public Equipo(String nombre){
		this.nombre = nombre;
		this.estadio = new Estadio();
		this.nivel = 1;
		aforoBase = 100;
	}
	public int aforoBase(){
		return aforoBase;
	}
	public void resetPreparatorias(){
		preparacion = null;
	}
	public int nivel(){
		return nivel;//el nivel de juego medio del equipo
	}
	public double versatilidad(){
		return versatilidad;
	}
	public double indiceOfensivo(){
		return indiceOfensivo;
	}
	public void setIOf(double IndOf){
		indiceOfensivo=IndOf;
	}
	public double indiceDefensivo(){
		return indiceDefensivo;
	}
	public void setIDf(double IndDf){
		indiceDefensivo=IndDf;
	}
	public double indicePosicional(){
		return indicePosicional;
	}
	public double indiceOportunista(){
		return indiceOportunista;
	}
	public double indiceContraOf(){
		return indiceContraOf;
	}
	public double indiceOptimismo(){
		return indiceOptimismo;
	}
	public double indiceFrialdad(){
		return indiceFrialdad;}
	
	public double indiceViloencia(){
		return indiceViloencia;
	}
	public int orgullo(){
		return orgullo;
	}
	

}
