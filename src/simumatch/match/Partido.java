package simumatch.match;

import java.util.ArrayList;
import java.util.List;

import simumatch.team.Equipo;
import simumatch.datamanager.Effect;

public class Partido {
	public Equipo local, visitante;
	int aforoL, aforoV;
	int animoL, animoV;
	int tacL, tacV;
	int marL=0; int marV=0;
	int equilibrio;
	Arbitro arbitro = new Arbitro();
	int duracion = 10;//el numero de turnos que va a durar el partido
	public Turno turno[] = new Turno[duracion];
	int turnoActual = -1;
	List<Effect> activas;//Hay que llevar un contador con los turnos que les quedan, y ejecutarlas como AccTurno cada turno
	private Memento mementer= new Memento(this);
	
	//Metodos Publicos (solo os interesan estos 2)
	public Partido(Equipo loc, Equipo vis){
		aforoL = local.aforoBase();
		aforoV = visitante.aforoBase();
		int maximLoc = loc.estadio.getAforoEqL();
		int maximVis = loc.estadio.getAforoEqV();
		ejecuta(loc.preparacion, true);
		ejecuta(vis.preparacion, false);
		aforoL = Math.min(maximLoc, aforoL);
		aforoV = Math.min(maximVis, aforoV);
		local=loc;
		visitante=vis;
		animoL = loc.orgullo();
		animoV = vis.orgullo();
		//el punto de equilibrio del partido (el estado que se mantiene si nadie hace nada)
		equilibrio = estadoEstable(loc,vis);
		if(equilibrio>=0){tacL=1;tacV=2;}
		 else			 {tacL=2;tacV=1;}
		
		arbitro = new Arbitro();
		loc.resetPreparatorias();
		vis.resetPreparatorias();
	}
	public Turno turno(List<Effect> accLoc, List<Effect> accVis){
		
		if(turnoActual++>duracion){
			System.out.println("El partido ya ha acabado");
			return null;
		}
		
		ejecuta(accLoc, true);
		ejecuta(accVis, false);
		ejecutaActivas();
		
		recalculaTacticas();
		recalculaAnimo();
		
		if(turnoActual>0)
			turno[turnoActual] = new Turno(generaTurno(calculaAbanico()));
		else turno[0]= new Turno(this);
		
		mementer.restaura();
		
		return turno[turnoActual];
	}
	//Privado. No pasar. Su lectura puede producir daños neurologicos permanentes
	public static int estadoEstable(Equipo loc, Equipo vis) {
		return(int)Math.round(Math.log(loc.nivel())-Math.log(vis.nivel()));
	}
	Turno actual(){
		return turno[turnoActual];
	}
	private void ejecuta(List<Effect> acc, boolean loc) {
		List<Effect> turno, partido;
		turno = vacia();
		partido= vacia();
		Effect a;
		while(!acc.isEmpty()){
			a = acc.get(0);
			switch (a.tiempo_efecto){
				case Const.TURNO: turno.add(a);break;
				case Const.PARTIDO: partido.add(a); break;
				default: System.out.println("No estan implementadas");
			}
			acc.remove(0);
		}
		ejecutaPerma(partido, loc);
		ejecutaTurno(turno, loc);
	}
	private void ejecutaTurno(List<Effect> inst, boolean loc) {
		Effect a;
		boolean local= loc;
		while(!inst.isEmpty()){
			a=inst.get(0);
			if(a.objetivo==Const.ENEMIGO)loc=!loc;
			
			bonifTem(a.tipo, a.bonificaci�n, a.entero, local);
			inst.remove(0);
			if(a.objetivo==Const.GLOBAL){
				a.objetivo=Const.ENEMIGO;
				inst.add(a);
			}
			loc=local;
		}
		
	}
	private void ejecutaPerma(List<Effect> acc, boolean local) {
		Effect a;
		boolean loc= local;
		while(!acc.isEmpty()){
			a=acc.get(0);
			if(a.objetivo==Const.ENEMIGO)loc=!loc;
			switch(a.atributo){
				case Const.AFORO:
					if(loc)aforoL=bonifPer(aforoL, a.bonificaci�n, a.entero);
					else  aforoV =bonifPer(aforoV, a.bonificaci�n, a.entero);
				break;
				case Const.NIVEL_EQUIPO:
					equilibrio= bonifPer(equilibrio, (loc?1:(-1))*a.bonificaci�n, a.entero);
				break;
				case Const.ORGULLO:
					if(loc)animoL=bonifPer(animoL, a.bonificaci�n, a.entero);
					else  animoV =bonifPer(animoV, a.bonificaci�n, a.entero);
				break;
				default:
					System.out.println("No implementadas acciones permanentes de tipo "+a.tipo);
			}
			acc.remove(0);
			if(a.objetivo==Const.GLOBAL){
				a.objetivo=Const.ENEMIGO;
				acc.add(a);
			}
			loc=local;
		}
	}
	private int bonifPer(int base, double bono, boolean suma) {
		if(suma)return base+=bono;
		return base*=bono;
	}
	private double bonifPer(double base, double bono, boolean suma) {
		if(suma)return base+=bono;
		return base*=bono;
	}
	private void bonifTem(int tipo, double bono, boolean suma, boolean local) {
		mementer.bonifTemp(tipo, bono, suma, local);
	}
	private int goles(){
		return marL-marV;
	}
	private void recalculaAnimo() {
		animoL+=goles();
		animoV-=goles();
		int estado = actual().estado;
		if(estado>0){
			animoL+=estado*local.indiceOptimismo();
			animoV-=estado/visitante.indiceFrialdad();
		}else
		if(estado<0){
			animoV+=estado*visitante.indiceOptimismo();
			animoL-=estado/local.indiceFrialdad();
		}
		animoL=(animoL+aforoL)/2;
		animoV=(animoV+aforoV)/2;
	}
	private void recalculaTacticas() {
		int estado = actual().estado;
		if(estado<0){
			if(tacL==1 && Math.random()<local.versatilidad())tacL=2;
			if(tacV==2 && Math.random()<visitante.versatilidad())tacV=1;
		}
		if(estado>0){
			if(tacL==2 && Math.random()<local.versatilidad())tacL=1;
			if(tacV==1 && Math.random()<visitante.versatilidad())tacV=2;
		}
	}
	private void ejecutaActivas() {
		// TODO Auto-generated method stub
		
	}
	private int generaTurno(double[] abanico){
		int i, estado;
		if(turnoActual==duracion/2)
			return equilibrio;
		double tirada = Math.random();
		for (i=0; tirada>0; i++)
			tirada-=abanico[i];
		estado = Partido.puntToEst(i);
		if(estado>5)marL++;
		if(estado<1)marV++;
		return estado;
	}

	private double[] calculaAbanico() {
		double abanico[] = new double [13];
		int eAnt, pAnt;
		eAnt = (turnoActual>0)?turno[turnoActual-1].estado:equilibrio;
		pAnt = puntToEst(eAnt);
		double l = abanico.length;
		for(int i=0; i<l; i++)abanico[i]=100;
		
		double indicess[] = {6, 4.5, 3, 2.5, 1.5};
		mul_adyacen(indicess,   pAnt, abanico);

		if(eAnt>0){
			if(tacL==1)bonif(pAnt, 13, local.indiceOfensivo(),abanico);
			if(tacV==2)bonif(0, pAnt, 1/visitante.indiceDefensivo(),abanico);
		}else
		if(eAnt<0){
			if(tacL==2)bonif(0, pAnt, 1/local.indiceDefensivo(),abanico);
			if(tacV==1)bonif(pAnt, 13, visitante.indiceOfensivo(),abanico);
		}
		
		double indices[] = {5,4,3,2};
		mul_adyacen(indices, estToPunt(equilibrio), abanico);
	
		
		bonif(pAnt+1, 13, Math.max(1,animoL/animoV), abanico);
		bonif( 0, pAnt-1, Math.max(1,animoV/animoL), abanico);
		
		
		return normalizar(abanico);
	}
	private void bonif(int origen, int destino, double mult, double aba[]){
		if(origen<0)origen=0;
		if(destino>13)destino=12;
		for(int i=origen; i<destino;i++ )
			aba[i]*=mult;
	}
	private static double[] normalizar(double[] input) {
		int len=  input.length;
		double output[] = new double[len];
		double sum = 0;
		for(int i=0; i<len; sum+=input[i++]);
		for(int i=0; i<len; i++)
			output[i]=input[i]/sum;
		return output;
	}
	private static void mul_adyacen(double indices[], int p, double abanico[]){
		for(int i=0; i<indices.length; i++)
			mul_adyacen(indices[i], p, i, abanico);
	}
	private static void mul_adyacen(double indice, int p, int g, double abanico[]) {
		int ps[]= adyacentes(p,g);
		for(int i=0; i<ps.length; i++)abanico[i]*=indice;
	}
	private static int[] adyacentes(int punto, int grado) {
		if(grado>12)return null;
		int[] r = new int[2];
		int s=1;
		boolean b;
		if(b=punto<punto+grado)r = new int[s--];
		if(punto<14-grado)r[s]=punto+grado;
		else r = new int[1];
		if(b)r[0]=punto-grado;
		return r;//debuelve un vector con los DOS PUNTOS separados "grado" de "punto"
	}
	static int puntToEst(int p){return p-6;}
	static int estToPunt(int e){return e+6;}
	static List<Effect> vacia(){
		ArrayList<Effect> ar = new ArrayList<Effect>();
		return ar;
	}
	private class Memento{
		Partido p;
		private double indOfL, indOfV ;
		private double indDeL, indDeV;
		int equilibrio;
		int animoL, animoV;
		boolean modificated=false;
		boolean inited=false;
	
		public void bonifTemp(int tipo, double bono, boolean suma, boolean loc) {
			switch (tipo){
			case Const.AFORO:
				System.out.println("El partido ya esta en marcha, no se puede modificar el aforo");
				break;
			case Const.NIVEL_EQUIPO:
				init();
				modificated=true;
				p.equilibrio= bonifPer(equilibrio, (loc?1:(-1))*bono, suma);
				break;
			case Const.ORGULLO:
				init();
				modificated=true;
				if(loc)p.animoL =bonifPer(animoL, bono, suma);
				else p.animoV =bonifPer(animoV, bono, suma);
				break;
			case Const.FACTOR_OFENSIVO:
				init();
				modificated=true;
				if(loc)p.local.setIOf(bonifPer(indOfL, bono, suma));
				else p.visitante.setIOf(bonifPer(indOfL, bono, suma));
				break;
			case Const.FACTOR_DEFENSIVO:
				init();
				modificated=true;
				if(loc)p.local.setIDf(bonifPer(indDeL, bono, suma));
				else p.visitante.setIDf(bonifPer(indDeL, bono, suma));
				break;
			default:
				System.out.println("No implementadas acciones permanentes de tipo "+tipo);
			}
			
		}
		
		Memento(Partido par){this.p= par;}
	
		public void restaura(){
			if(!modificated)return;
			if(!inited)System.out.println("No se ha iniciado el turno antes de los cambios, se han perdido los datos originales");
			p.animoL= animoL;
			p.animoV= animoV;
			p.equilibrio= equilibrio;
			p.local.setIOf(indOfL);
			p.local.setIDf(indDeL);
			p.visitante.setIOf(indOfV);
			p.visitante.setIDf(indDeV);
			inited=false;
			modificated=false;	
		}
		public void init(){
			if(inited)return;
			if(modificated){
				System.out.println("Se ha reiniciado el turno antes de restaurarlo, restauracion automatica");
				restaura();
			}	
			animoL= p.animoL;
			animoV= p.animoV;
			equilibrio= p.equilibrio;
			indOfL= local.indiceOfensivo();
			indDeL= local.indiceDefensivo();
			indOfV= visitante.indiceOfensivo();
			indDeV= visitante.indiceDefensivo();
			inited=true;
			modificated=false;
		}	
	}
	


}
