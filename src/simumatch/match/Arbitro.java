package simumatch.match;

//import simumatch.Equipo;

class Arbitro{
	double atencion;//probabilidad de pillar las faltas
	double sesgo;//subjetividad
	double influencia;//influencia en el balanceado
	//Equipo sobornado = null;
	Arbitro(double a, double s, double i){
		atencion=a; sesgo=s; influencia=i;
	}
	Arbitro(){
		atencion = 0.8 *Math.max (Math.random(), Math.random());
		sesgo = 0.1*(Math.random()-0.5);//entre el -5% y el 5%
		influencia = 0.1*(1+(Math.random()-0.5));//0.1 +/- 0.05
	}
	/*boolean activar_soborno(Equipo e){
		if(e!=sobornado)return false;
		return true;
	}*/
}