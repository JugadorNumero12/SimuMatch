package simumatch.match;

import java.util.List;

import simumatch.team.Equipo;
import simumatch.common.*;

public class Partido {
	public Equipo local, visitante;
	int aforoL, aforoV;
	int animoL, animoV;
	int tacL, tacV;
	int marL=0; int marV=0;
	int equilibrio;
	Arbitro arbitro = new Arbitro();
	int duracion = 10;
	int descanso = duracion/2+(duracion%2);
	int turnoActual = 0;
	private Memento mementer= new Memento(this);
	private Turno turno[] = new Turno[duracion+1];
	//List<Effect> activas=vacia();//Hay que llevar un contador con los turnos que les quedan, y ejecutarlas como AccTurno cada turno
	
	
	//Metodos Publicos (solo os interesan estos 2)
	/**
	 * Crea un partido a partir de los equipos que lo juegan
	 * */
	public Partido(Equipo loc, Equipo vis){
		local=loc;
		visitante=vis;
		arbitro = new Arbitro();
		
		aforoL = loc.aforoBase();
		aforoV = vis.aforoBase();
		animoL = loc.orgullo();
		animoV = vis.orgullo();
		
		equilibrio = estadoEstable(loc,vis);
		
		if(equilibrio>=0){tacL=1;tacV=2;}
		 else			 {tacL=2;tacV=1;}
		
		ejecuta(loc.getPreparatorias(), true);
		ejecuta(vis.getPreparatorias(), false);
		
		aforoL = Math.min(loc.estadio.getAforoL(), aforoL);
		aforoV = Math.min(loc.estadio.getAforoV(), aforoV);

		loc.resetPreparatorias();
		vis.resetPreparatorias();
		
		double[] firstAbanico = {0,0,0,0,0,0,0,0,0,0,0,0,0};
		firstAbanico[estToPunt(equilibrio)]=1;
		
		turno[0]= new Turno(equilibrio, this, firstAbanico);
	}
	/**
	 *Genera un turno nuevo a partir de las acciones realizadas por cada equipo
	 */
	public Turno turno(List<Effect> accLoc, List<Effect> accVis){
		
		if(++turnoActual>duracion){
			System.out.println("El partido ya ha acabado");
			return null;
		}
		
		recalculaAnimo();
		recalculaTacticas();
		
		ejecuta(accLoc, true);
		ejecuta(accVis, false);
		//TODO ejecutaActivas(); 
		
		double [] abanico= calculaAbanico();
		turno[turnoActual] = new Turno(generaTurno(abanico), this, abanico);
	
		mementer.restaura();
		
		return turno[turnoActual];
	}
	
	//Privados. ADVETENCIA: Su lectura puede producir daños neurologicos permanentes.
	
	public static int estadoEstable(Equipo loc, Equipo vis) {
		return(int)Math.round(Math.log(loc.nivel())-Math.log(vis.nivel()));
	}
	Turno anterior(){
		return turno[turnoActual-1];
	}
	private void ejecuta(List<Effect> acc, boolean loc) {
		for(Effect a: acc) ejecutaEffect(a, loc, a.isPermanent());
	}
	private void ejecutaEffect(Effect a, boolean loc, boolean perm) {
			if(a.getTarget()!=Target.OPPONENT)
				if(perm)bonifPer(a.getScope(), a.getBonus(), a.getOperator(), loc);
				else bonifTem(a.getScope(), a.getBonus(), a.getOperator(), loc);
			if(a.getTarget()!=Target.SELF)
				if(perm)bonifPer(a.getScope(), a.getBonus(), a.getOperator(), !loc);
				else bonifTem(a.getScope(), a.getBonus(), a.getOperator(), !loc);
	}
	private void bonifPer(Scope scope, double bonus, Operator op, boolean loc) {
		switch(scope){
		case PEOPLE:
			if(loc)aforoL= op.apply(aforoL, bonus);
			else   aforoV= op.apply(aforoV, bonus);
			break;
		case TEAM_LEVEL:
			equilibrio= applyEquil(equilibrio, bonus, op, loc);
			if(mementer.inited)mementer.equilibrio=applyEquil(mementer.equilibrio, bonus, op, loc);
			break;
		case ATMOSPHERE:
		case ENCOURAGE:
			if(loc){
				animoL= op.apply(animoL, bonus);
				if(mementer.inited)mementer.animoL=op.apply(mementer.animoL, bonus);
			}else{
				animoV= op.apply(animoV, bonus);
				if(mementer.inited)mementer.animoV=op.apply(mementer.animoV, bonus);
			}
			break;
		case OFFENSIVE_SPIRIT:
			if(loc){
				local.setIOf(op.apply(local.indiceOfensivo(), bonus));
				if(mementer.inited)mementer.indOfL=op.apply(mementer.indOfL, bonus);
			}else{
				visitante.setIOf(op.apply(visitante.indiceOfensivo(), bonus));
				if(mementer.inited)mementer.indOfV=op.apply(mementer.indOfV, bonus);
			}	
			break;
		case DEFENSIVE_SPIRIT:
			if(loc){
				local.setIDf(op.apply(local.indiceDefensivo(), bonus));
				if(mementer.inited)mementer.indDeL=op.apply(mementer.indDeL, bonus);
			}else{
				visitante.setIDf(op.apply(visitante.indiceDefensivo(), bonus));
				if(mementer.inited)mementer.indDeV=op.apply(mementer.indDeV, bonus);
			}
			break;
		default:
			System.out.println("No implementadas acciones permanentes de tipo "+scope);
		}
	}
	private void bonifTem(Scope scope, double bonus, Operator op, boolean loc) {
		this.mementer.bonifTemp(scope, bonus, op, loc);
	}
	private static int applyEquil(int equilib, double bonus, Operator op, boolean loc){
		String operador=op.toString();
		if(operador=="+"||operador=="-")
			return op.apply(equilib, loc?bonus:-bonus);
		if(operador=="*"||operador=="/")
			return op.apply(equilib, loc?bonus:1/bonus);
		System.out.println("Operador desconocido, los valores pueden ser erroneos");
		return op.apply(equilib, bonus);
	}
	int goles(){
		return marL-marV;
	}
	private void recalculaAnimo() {
		animoL+=goles()*10;
		animoV-=goles()*10;
		int estado = anterior().getEstado();
		if(estado>0){
			animoL+=estado*local.indiceOptimismo();
			animoV-=estado/visitante.indiceFrialdad();
		}else
		if(estado<0){
			animoV-=estado*visitante.indiceOptimismo();
			animoL+=estado/local.indiceFrialdad();
		}
		animoL=(2*animoL+aforoL)/3;
		animoV=(2*animoV+aforoV)/3;
	}
	private void recalculaTacticas() {
		int estado = anterior().getEstado();
		if(estado<0){
			if(tacL==1 && Math.random()<local.versatilidad())tacL=2;
			if(tacV==2 && Math.random()<visitante.versatilidad())tacV=1;
		}
		if(estado>0){
			if(tacL==2 && Math.random()<local.versatilidad())tacL=1;
			if(tacV==1 && Math.random()<visitante.versatilidad())tacV=2;
		}
	}
	private int generaTurno(double[] abanico){
		if(turnoActual==duracion/2)
			return equilibrio;
		int estado, i=0;
		for (double tirada= Math.random(); tirada>0; i++)
			if((tirada-=abanico[i]) <=0) break;
		estado = Partido.puntToEst(i);
		if(estado> 5)marL++;
		if(estado<-5)marV++;
		return estado;
	}
	/**
	 * Metodo para generar el abnico de probabilidades
	 * devuelve un abanico con una probabilidad asociada a cada uno de los 13 estados
	 */
	private double[] calculaAbanico() {
		double abanico[] = new double [13];
		int eAnt, pAnt; //estado anterior, el punto del abanico correspondiente
		eAnt = (turnoActual>0)?turno[turnoActual-1].getEstado():equilibrio;
		pAnt = estToPunt(eAnt);
		int l = abanico.length;
		for(int i=0; i<l; i++)abanico[i]=100;//inicialízo el abanico con valores iguales distintos de 0
		
		
		if(-6<eAnt && eAnt>6){
			//si se marca gol, no se aplica este bonificador
			double indi_est[] = {6, 4.5, 3, 2.5, 1.5};
			mul_adyacen(indi_est, pAnt, abanico);
			//el estado anterior multiplica su prob por 6
			//los dos estados adyacentes multiplican por 4.5
			//los dos estados a distancia 2 del eAnt multiplican por 3
			//los dos estados a distancia 3 del eAnt multiplican por 2.5
			//los dos estados a distancia 4 del eAnt multiplican por 1.5
		}

		if(eAnt>0){//el Local lleva la iniciativa
			if(tacL==1)//si local tiene tactica ofensiva aplica el bono
				bonif(pAnt, l, local.indiceOfensivo(),abanico);
			if(tacV==2)//si visitante tiene tactica defensiva penaliza al local
				bonif(pAnt, l, 1/visitante.indiceDefensivo(),abanico);
		}else
		if(eAnt<0){//el Visitante lleva la iniciativa
			if(tacV==1)//si visitante tiene tactica ofensiva aplica el bono
				bonif(0, pAnt, visitante.indiceOfensivo(),abanico);
			if(tacL==2)//si local tiene tactica defensiva penaliza al visitante
				bonif(0, pAnt, 1/local.indiceDefensivo(),abanico);
		}
		
		double indi_equi[] = {2.75, 2, 1.25};
		mul_adyacen(indi_equi, estToPunt(equilibrio), abanico);
		//el estado de equilibrio multiplica su prob por 2.75
		//los dos estados adyacentes multiplican por 2
		//los dos estados a distancia 2 del equilibrio multiplican por 1.25
	
		//la fraccion de animos bonifica a los 3 estados más proximos al actual
		bonif(pAnt+1, pAnt+3, Math.max(0, animoL/animoV), abanico);
		bonif(pAnt-3, pAnt-1, Math.max(0, animoV/animoL), abanico);
		
		return normalizar(abanico);
	}
	/**
	 * Dado un vector aba, un numero mult y unos indices origen y destino
	 * multiplica todos los elementos entre "origen" y "destino" por "mult" 
	 */
	private static void bonif(int origen, int destino, double mult, double aba[]){
		if(origen<0)origen=0;
		if(destino>12)destino=12;
		for(int i=origen; i<=destino; i++)
			aba[i]*=mult;
	}
	/**
	 * Dado un vector devuelve uno proporcional 
	 * para el que el sumatorio de los elementos == 1
	 */
	private static double[] normalizar(double[] input) {
		int len = input.length;
		double output[] = new double[len];
		double sum = 0;
		for(int i=0; i<len; i++)
			if(input[i]<0)System.out.println("Warning! franja negativa en el abanico");
			else sum+=input[i];
		for(int i=0; i<len; i++)
			output[i]=Math.max(input[i]/sum, 0);
		return output;
	}
	/**
	 * Dado un vector "prods" de multiplicadores y un punto "p" de un "abanico"
	 * aplica los multiplicadores desde "p" en puntos adyacentes cada vez más alejados
	*/
	private static void mul_adyacen(double prods[], int p, double abanico[]){
		for(int i=0; i<prods.length; i++)
			mul_adyacen(prods[i], p, i, abanico);
	}
	private static void mul_adyacen(double prod, int p, int g, double abanico[]) {
		int ps[]= adyacentes(p, g);
		for(int i=0; i<ps.length; i++)abanico[ps[i]]*=prod;
	}
	/**
	 * Dado un "punto" y una distancia "grado"
	 * devuelve los dos puntos alejados "grado" de "punto"
	 */
	private static int[] adyacentes(int punto, int grado){
		int g = Math.abs(grado);
		if(g>12)return null;
		if(g==0){
			int self[]={punto};
			return self;
		}
		int[] r = new int[2];
		int s=1;
		boolean b = punto-g < 0;
		if(b ) r = new int[s--];
		if(punto+g < 13)  r[s]=punto+g;
		else r = new int[1];
		if(!b) r[0]=punto-g;
		return r;
	}
	static int puntToEst(int p){return p-6;}
	static int estToPunt(int e){return e+6;}
	private class Memento{
		Partido p;
		private double indOfL, indOfV ;
		private double indDeL, indDeV;
		int equilibrio;
		int animoL, animoV;
		boolean modificated = false;
		boolean inited = false;
	
		void bonifTemp(Scope atrib, double bono, Operator op, boolean loc) {
			switch (atrib){
			case PEOPLE:
				System.out.println("El partido ya esta en marcha, no se puede modificar el aforo");
				break;
			case TEAM_LEVEL:
				init();
				modificated=true;
				p.equilibrio= applyEquil(p.equilibrio, bono, op, loc);
				break;
			case ATMOSPHERE:
			case ENCOURAGE:
				init();
				modificated=true;
				if(loc)p.animoL =op.apply(p.animoL, bono);
				else   p.animoV =op.apply(p.animoV, bono);
				break;
			case OFFENSIVE_SPIRIT:
				init();
				modificated=true;
				if(loc)p.local.setIOf(op.apply(p.local.indiceOfensivo(), bono));
				else p.visitante.setIOf(op.apply(p.visitante.indiceOfensivo(), bono));
				break;
			case DEFENSIVE_SPIRIT:
				init();
				modificated=true;
				if(loc)p.local.setIDf(op.apply(p.local.indiceDefensivo(), bono));
				else p.visitante.setIDf(op.apply(p.visitante.indiceDefensivo(), bono));
				break;
			default:
				System.out.println("No implementadas acciones permanentes de tipo "+atrib);
			}
		}
		
		Memento(Partido par){this.p= par;}
	
		void restaura(){
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
		void init(){
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
	}//end of Memento
	


}//end of Partido
