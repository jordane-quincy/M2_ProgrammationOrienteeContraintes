package com.github;

import java.util.HashMap;
import java.util.Map;

public class Solution {
	Map<Variable, Integer> solution = new HashMap<Variable, Integer>();
	CSP csp;
	public Solution(CSP csp) {
		this.csp = csp;
		//Initialisation solution to null for each variable
		for (Variable i : csp.getLstVariable()) {
			solution.put(i, -1);
		}
	}
	
	public void setValueToVariable (Variable variable, int v) {
		solution.replace(variable, v);
	}
	
	public int getValueOfVariable (Variable v) {
		return solution.get(v);
	}
	
	public boolean authorizedValueForVariable (Variable variable, int v) {
		//On parcours la liste des contraintes
		for (Contrainte c : csp.getLstContrainte()) {
			//Si la variable qu'on veut tester appartient à une contrainte (variable1 ou variable2 de la contrainte)
			if (c.getVariable1().equals(variable)) {
				boolean oneCoupleMatch = false;
				//On parcours la liste des couples de la contraintes
				for (CoupleValeur couple : c.getLstCouple()) {
					//Si au moins un couple autorise cette valeur pour cette variable en fonction de ce qu'on a déjà dans la solution alors on continue, sinon on retourne faux
					if ((couple.getValeur2() == -1 || couple.getValeur2() == getValueOfVariable(c.getVariable2())) && couple.getValeur1() == v) {
						oneCoupleMatch = true;
					}
				}
				if (!oneCoupleMatch) {
					return false;
				}				
			}
			if (c.getVariable2().equals(variable)) {
				boolean oneCoupleMatch = false;
				//On parcours la liste des couples de la contraintes
				for (CoupleValeur couple : c.getLstCouple()) {
					//Si au moins un couple autorise cette valeur pour cette variable en fonction de ce qu'on a déjà dans la solution alors on continue, sinon on retourne faux
					if ((couple.getValeur1() == -1 || couple.getValeur1() == getValueOfVariable(c.getVariable1())) && couple.getValeur2() == v) {
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
}
