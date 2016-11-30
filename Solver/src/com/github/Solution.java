package com.github;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Solution {
	Map<Integer, CoupleValueDomain> solution = new HashMap<Integer, CoupleValueDomain>();
	CSP csp;
	
	public Solution(CSP csp) {
		this.csp = csp;
		//Initialisation solution to null for each variable
		for (Variable i : csp.getLstVariable()) {
			solution.put(i.getIdInt(), new CoupleValueDomain(-1, new ArrayList<Integer>(i.getDomaine())));
		}
	}
	
	public Solution(Solution s) {
		this.csp = s.getCsp();
		//Copie de s.getSolution()
		Map<Integer, CoupleValueDomain> tmpSolution = new HashMap<Integer, CoupleValueDomain>(s.getSolution());
		Map<Integer, CoupleValueDomain> newSolution = new HashMap<Integer, CoupleValueDomain>();		
		for (Integer mapKey : tmpSolution.keySet()) {
			newSolution.put(mapKey, new CoupleValueDomain(tmpSolution.get(mapKey)));
		}
		this.solution = newSolution;
	}
	
	public CSP getCsp() {
		return csp;
	}

	public void setCsp(CSP csp) {
		this.csp = csp;
	}

	public void setValueToVariable (int idVariable, int v) {
		solution.get(idVariable).setValue(v);
	}
	
	public int getValueOfVariable (Variable v) {
		return solution.get(v.getIdInt()).getValue();
	}
	
	public boolean authorizedValueForVariable (int idVariable, int v) {
		//On parcours la liste des contraintes
		for (Contrainte c : csp.getLstContrainte()) {
			//Si la variable qu'on veut tester appartient à une contrainte (variable1 ou variable2 de la contrainte)
			if (c.getVariable1().getIdInt() == idVariable) {
				boolean oneCoupleMatch = false;
				//On parcours la liste des couples de la contraintes
				for (CoupleValeur couple : c.getLstCouple()) {
					//Si au moins un couple autorise cette valeur pour cette variable en fonction de ce qu'on a déjà dans la solution alors on continue, sinon on retourne faux
					if ((getValueOfVariable(c.getVariable2()) == -1 || couple.getValeur2() == getValueOfVariable(c.getVariable2())) && couple.getValeur1() == v) {
						oneCoupleMatch = true;
					}
				}
				if (!oneCoupleMatch) {
					return false;
				}				
			}
			if (c.getVariable2().getIdInt() == idVariable) {
				boolean oneCoupleMatch = false;
				//On parcours la liste des couples de la contraintes
				for (CoupleValeur couple : c.getLstCouple()) {
					//Si au moins un couple autorise cette valeur pour cette variable en fonction de ce qu'on a déjà dans la solution alors on continue, sinon on retourne faux
					if ((getValueOfVariable(c.getVariable1()) == -1 || couple.getValeur1() == getValueOfVariable(c.getVariable1())) && couple.getValeur2() == v) {
						oneCoupleMatch = true;
					}
				}
				if (!oneCoupleMatch) {
					return false;
				}
			}
		}
		return true;
	}

	public Map<Integer, CoupleValueDomain> getSolution() {
		return solution;
	}

	public void setSolution(Map<Integer, CoupleValueDomain> solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		return "Solution [" + solution + "]";
	}
	
	
}
