package simumatch.datamanager;

import java.util.Comparator;

import simumatch.common.Effect;
import simumatch.common.Operator;


public class ComparatorEffect implements Comparator<Object>{

	@Override
	public int compare(Object effect1, Object effect2) {	
								
		if(((Effect) effect1).getOperator()==Operator.PRODUCT && ((Effect) effect2).getOperator()!=Operator.PRODUCT) return -1;
		else{
			if(((Effect) effect1).getOperator()!=Operator.PRODUCT && ((Effect) effect2).getOperator()==Operator.PRODUCT) return 1;
			else return 0;
		}
	}

}
