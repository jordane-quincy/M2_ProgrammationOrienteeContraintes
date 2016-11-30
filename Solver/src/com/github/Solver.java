package com.github;

import java.util.ArrayList;
import java.util.List;

public class Solver {
	public static List<Solution> resolveByBacktracking (CSP csp) {
		List<Solution> allSolutions = new ArrayList<Solution>();
		Solution s = new Solution(csp);
		//System.out.println(csp);
		//proc�dure BT
		//d�but
		int i = 1;
		while (i <= csp.getNbVariables() && i >= 1) {
			int x;
			boolean ok = false;
			CoupleValueDomain solForVarI = s.getSolution().get(i);
			List<Integer> domaine = solForVarI.getDomaine();
			while (!ok && domaine.size() > 0) {
				x = domaine.get(0);
				domaine.remove(0);
				solForVarI.setDomaine(domaine);
				if (s.authorizedValueForVariable(i, x)) {
					s.setValueToVariable(i, x);
					ok = true;
				}				
			}
			if (!ok) {
				//Si on n'a pas trouv� de valeur compatible avec la solution courante
				//On reset le Domaine de la variable et on repasse � la variable pr�c�dente
				solForVarI.setDomaine(new ArrayList<Integer>(csp.getLstVariable().get(i - 1).getDomaine()));
				i--;
			}
			else {
				if (i == csp.getNbVariables()) {
					//Dans ce cas on a une solution, il faut la save
					//Puis enlev� la valeur dans la solution en cours
					//pour continuer le parcours
					allSolutions.add(new Solution(s));
					s.setValueToVariable(i, -1);
				}
				else {
					i++;
				}
			}
		}
		//finProc�dure
		return allSolutions;
	}
}
